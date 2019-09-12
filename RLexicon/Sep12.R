require(httr)
require(stringi)
require(XML) #htmlParse
require(htm2txt)
require(RMySQL)
require(xml2) #read_html

#process wikipedia pages from a OneTab link in order to send them into a database and perform tfidf on them.

NamesRevsPurps <- function(url, purpose){#url is a OneTab url that has all the wikipedia links. purpose is a string that will go to the purpose column in MySQL for each document represented by the links in the OneTab url. In next iteration I will make it so that you can use any list of links in HTML, or any list of links.
  
  revs <- function(name, lang){ #GET requires httr
    request<-GET(paste0("https://", lang, ".wikipedia.org/w/api.php?action=query&prop=revisions&titles=",name,"&rvprop=ids&format=xml"))
    parsedReq<-xmlParse(content(request,"text"))
    revresult <- xpathSApply(parsedReq,"string(//rev/@revid)")
    revresult
  }
  
  request <- GET(url)
  rawcontent <- htmlParse(content(request,"text"))
  #above two lines can be simplified to read_html i think???
  linkFormat<-xpathSApply(rawcontent, "//a/@href")[c(-1,-2)]
  names<-gsub("h.*?i/([^/]*?)$","\\1",linkFormat)
  langs <- gsub("h.*?//(.*?)[.]wiki.*","\\1",linkFormat)
  revisions<-vector()
  for (i in 1:length(names)){
    revisions[i] <- revs(names[i], langs[i])
  }
  nameslangs <- cbind(names,revisions,langs,purpose)
  dfnamlang<- data.frame(nameslangs)
  rownames(dfnamlang)<-c()
  print(dfnamlang)
  View(dfnamlang)

  # dnames$revisions <- revisions
  # dnames$purpose <- purpose
  # dnames
  # print(dnames)
  # print(dnames$langs)
} #ok, now time to process the df with other functions

wikiProcess <- function(oldid){#oldid must be a char
  wikiquest <- read_html(paste0("https://en.wikipedia.org/w/index.php?oldid=",oldid))#rvest
  wikipage <- htmlParse(wikiquest)#xml
  wikidesired <- xpathSApply(wikipage, "//div[@class='mw-parser-output']/*[count(following-sibling::h2//span[.='References'])>0]",toString.XMLNode) #this doesn't work for all pages #possibly it works for all pages unless they're linked to a section, in which case the workflow probably failed upstream.
  info<-tolower(htm2txt(wikidesired))#htm2text
  paste(info, collapse=" ")
}

continuance<- function(charvector){#not sure how passing vars works in r but i wasnt able to correctly access the "global" variables residing in the outer function def from the inner function def
  print(c(charvector[1:10],"...",charvector[(length(charvector)-9):(length(charvector))]))
  addUnder100<- function(longChar, dataf, lc=0){
    if(nchar(longChar) > 100){
      dataf <- rbind(dataf,data.frame(word = substring(longChar, 1, 100), RC = 1, LC = lc))
      theRest <- substring(longChar, 101, nchar(longChar))
      addUnder100(theRest, dataf, lc=1)
    } else{
      dataf <- rbind(dataf,data.frame(word = longChar, RC = 0, LC = lc))
      dataf
    }
  }
  newdf <- setNames(data.frame(matrix(ncol = 3, nrow = 0)), c("word", "RC", "LC"))
  index<-1
  for (c in charvector){
    newdf <- addUnder100(c, newdf) 
  }
  newdf
}

putIntoDB <- function(name="testSep4", rev=0, purp="test", content, mydb){ #successor of giveToDB
  splitContent <-strsplit(content,split="[[:punct:][:space:]]")[[1]]
  splitWithoutBlanks <- splitContent[splitContent!=""]
  contentWithConts <- continuance(splitWithoutBlanks)
  rownum <- nrow(contentWithConts)
  if (rownum > 0){
    print(fetch(dbSendQuery(mydb, paste0("call rev_doc_add(\"",name,"\",\"",rev,"\",\"",purp,"\")")),n=-1))
    while(dbMoreResults(mydb)){
     throwawayResult<-fetch(dbNextResult(mydb), n=-1) 
    }
    for (r in 1:nrow(contentWithConts)){#figure out why this is only doing one word at a time (three rows for >200 char word)
      word <- contentWithConts[r,1]
      right <- contentWithConts[r,2]
      left <- contentWithConts[r,3]
      while(dbMoreResults(mydb)) {
        anotherResult<-dbNextResult(mydb)#throwaway. might be a duplicate of throwawayResult above, or maybe they're both necessary
      }
      eachquery<-dbSendQuery(mydb, paste0("call textstring_and_word_add(\"",word,"\")"))
      id <- dbGetQuery(mydb, "select last_insert_id();")[1,1]
      updatequery<- dbSendQuery(mydb,paste0("update textstring set left_continuance=",left,",right_continuance=",right," where word_id=",id))
      #print(id)
      #print(r)
      
    }
    print(paste("last word added into", name, "doc:"))
    print(fetch(dbSendQuery(mydb, "select * from textstring order by word_id desc limit 1"), n=-1))
  }
  print(paste("number of words", as.character(rownum)))
}

NamesRevsPurpsDB <- function(url, purpose, db){#do something for when the xpath in wikiProcess doesnt work # this might be because of when the link is to an article section
  df <- NamesRevsPurps(url, purpose)#add language processing
  # View(df)
  for (row in 1:nrow(df[2])){
    thisname <- df[row,1]
    thisrev <- as.character(df[row, 2])
    thispurp <- df[row, 3]#this column isnt necessary!!!
    thiscontent <- wikiProcess(thisrev)
    putIntoDB(name = thisname, rev = thisrev, purp=thispurp, content=thiscontent, mydb = db)#add language
    # filename <- paste0(df[row,1],"-",df[row,2],"-",df[row,3],".txt")
    # article <- wikiProcess(df[row,2])
    # write(article,filename)
    print(paste(thisname, thisrev, "logged into DB."))
  }
  #for each row{
  #wikiprocess
  #write text
}

tfidfCSV <- function(url, purpose, username, pw){
  db <- dbConnect(MySQL(), user=username, password=pw, dbname='lexicon', host='localhost', port=3307)
  NamesRevsPurpsDB(url, purpose, db)
  
  df<- fetch(dbSendQuery(db, paste0("select * from docs where purpose = \"",purpose,"\" order by doc_id desc limit 100")), n=-1)
  for(i in 1:nrow(df)){
    doc_id <- df[i,1]
    name <- df[i,2]
    rev <- df[i,4]
    purp <-df[i,5]
    tfidf <- fetch(dbSendQuery(db, paste0("call tfidf(",doc_id,")")), n=-1)#worth mentioning lots of times, that integers like doc_id max out at around 2 billion in MySQL. The highest ints so far are ~900 million
    while(dbMoreResults(db)){
      res1<- dbNextResult(db)
      unneededresult <- fetch(res1, n=-1)#throwaway
    }
    write.csv(tfidf, paste(name,rev,paste0(purp,".csv"), sep="-"))
  }
  dbDisconnect(db)
}
