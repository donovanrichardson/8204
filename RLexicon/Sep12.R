require(httr)
require(stringi)
require(XML) #htmlParse
require(htm2txt)
require(RMySQL)
require(xml2) #read_html

#process wikipedia pages from a OneTab link in order to send them into a database and perform tfidf on them.

NamesRevsPurps <- function(url, purpose){#url is a OneTab url that has all the wikipedia links. purpose is a string that will go to the purpose column in MySQL for each document represented by the links in the OneTab url. In later iteration I will make it so that you can use any list of links in HTML, or any list of links.
  
  revs <- function(name, lang){ #GET requires httr
    request<-GET(paste0("https://", lang, ".wikipedia.org/w/api.php?action=query&prop=revisions&titles=",name,"&rvprop=ids&format=xml")) #gets the wiki page
    parsedReq<-xmlParse(content(request,"text")) #xml
    revresult <- xpathSApply(parsedReq,"string(//rev/@revid)") #gets the rev number
    revresult
  }
  
  titles <- function(name, lang){ #like revs but for getting titles. especially important for non-latin alphabets
    request<-GET(paste0("https://", lang, ".wikipedia.org/w/api.php?action=query&prop=revisions&titles=",name,"&rvprop=ids&format=xml")) #gets the wiki page
    parsedReq<-xmlParse(content(request,"text")) #xml
    revresult <- xpathSApply(parsedReq,"string(//page/@title)") #gets the rev number
    revresult
  }
  
  request <- GET(url) #requests list of links page
  rawcontent <- htmlParse(content(request,"text")) #gets the html of page
  #above two lines can be simplified to read_html i think???
  linkFormat<-xpathSApply(rawcontent, "//a/@href")[c(-1,-2)] #gets links
  names<-gsub("h.*?i/([^/]*?)$","\\1",linkFormat) #gets wikipage names #please the names have to not be random html characters. use the api please #this involves getting the random page xpath for each locale #lol put that in schema too
  langs <- gsub("h.*?//(.*?)[.]wiki.*","\\1",linkFormat) #gets wikipage langs
  revisions<-vector()
  newnames<-vector()
  for (i in 1:length(names)){
    revisions[i] <- revs(names[i], langs[i])
    newnames[i]<- titles(names[i], langs[i])
  }
  nameslangs <- cbind(newnames,revisions,langs,purpose)
  dfnamlang<- data.frame(nameslangs)
  rownames(dfnamlang)<-c()
  dfnamlang
}

wikiProcess <- function(oldid, lang, db){#oldid must be a char
  wikiquest <- read_html(sprintf("https://%s.wikipedia.org/w/index.php?oldid=%s",lang,oldid))#rvest #request page
  wikipage <- htmlParse(wikiquest)#xml #gets html content of wikipage
  wikiContent <- "" #placeholder
  xPathIndex <- 1 #decides which row will be taken for xpath
  xPathMax <- dbGetQuery(db, sprintf("select sum(1) from xpath where lang_id = \"%s\"",lang))[1,1]
  xPathCandidates <- dbGetQuery(db, sprintf("select expression from xpath where lang_id = \"%s\"", lang), n=-1)$expression
  while (wikiContent == "" & xPathIndex <= xPathMax){
    wikiXPath <- xPathCandidates[xPathIndex]
    wikidesired <- xpathSApply(wikipage, wikiXPath ,toString.XMLNode)
    wikiContent<-tolower(htm2txt(wikidesired))#htm2text #lowercase formatting
    xPathIndex<- xPathIndex + 1
  }
  if (wikiContent == ""){ #throughout this function, is it better to say if string size is 0?
    stop("xpath") #provides error for correct handling in later function
    #the rest of the function is not executed in this case
  }
  paste(wikiContent, collapse=" ") 
}

continuance<- function(charvector){
  print(c(charvector[1:10],"...",charvector[(length(charvector)-9):(length(charvector))]))#prints first ten and alast ten characters in the charvector
  addUnder100<- function(longChar, dataf, lc=0){
    if(nchar(longChar) > 100){
      dataf <- rbind(dataf,data.frame(word = substring(longChar, 1, 100), RC = 1, LC = lc)) #if this word is >100 characters, adds row with RC=true (signifying more chars to the right of these 100 chars)
      theRest <- substring(longChar, 101, nchar(longChar))
      addUnder100(theRest, dataf, lc=1)#this recursion is gnarly lol
    } else{
      dataf <- rbind(dataf,data.frame(word = longChar, RC = 0, LC = lc))# if a word is 100 chars or shorter, it gets added to the dataframe with no right continuance and the appropriate left cont.
      dataf
    }
  }
  newdf <- setNames(data.frame(matrix(ncol = 3, nrow = 0)), c("word", "RC", "LC"))
  index<-1
  for (c in charvector){
    newdf <- addUnder100(c, newdf) #omg this is why my function is so slow fml. What kind of ridiculous n^2 recursion is this ffs
    #https://stackoverflow.com/questions/11486369/growing-a-data-frame-in-a-memory-efficient-manner
  }
  newdf
}

putIntoDB <- function(name="testSep13", rev=0, purp="test", content="", mydb, lang="en", error = F){
  print(fetch(dbSendQuery(mydb, sprintf("call rev_doc_add(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")",name, rev, purp, lang, error)),n=-1))#Add error to this procedure, as well as to the unique of the table DONE
  #nothing else happens if there is error 
  if (!error){
    splitContent<-
      if (lang=="ja" | lang == "zh"){#potentially other varieties of chinese. Need to handle non-western punctuation in the future. 
        splitContent <- strsplit(content,split="")[[1]]#for kanji-using languages, each individual character will be entered, regardless if it is kanji or not.
      }else{
        splitContent <- strsplit(content,split="[[:punct:][:space:]]")[[1]]#for example, korean uses spaces
      }
    splitWithoutBlanks <- splitContent[splitContent!=""]#look to see if whitespace also exists, especially in zh and ja
    contentWithConts <- continuance(splitWithoutBlanks) #fix this hot garbage
    rownum <- nrow(contentWithConts)
    while(dbMoreResults(mydb)){#don't know how this would happen if i fetched -1 before
      throwawayResult<-fetch(dbNextResult(mydb), n=-1) #in case there are just random uncollected results
    }
    for (r in 1:nrow(contentWithConts)){
      word <- contentWithConts[r,1]
      right <- contentWithConts[r,2]
      left <- contentWithConts[r,3]
      while(dbMoreResults(mydb)) {
        anotherResult<-dbNextResult(mydb)#throwaway. might be a duplicate of throwawayResult above, or maybe they're both necessary
      }
      eachquery<-dbSendQuery(mydb, paste0("call textstring_and_word_add(\"",word,"\")")) #adds individual word to database, with doc being the last added doc
      id <- dbGetQuery(mydb, "select last_insert_id();")[1,1]# the purpose of this is similar to dbNextResult  #in theory, is it possible that this would not yield the id that i want?
      updatequery<- dbSendQuery(mydb,paste0("update textstring set left_continuance=",left,",right_continuance=",right," where word_id=",id))#i might be able to make this more efficient by only calling when necessary
      #tbh it might be appropriate to do contentWithConts as a temp table in mysql bc that would do wonders for performance.
    }
    print(paste("last word added into", name, "doc:")) 
    print(fetch(dbSendQuery(mydb, "select * from textstring order by word_id desc limit 1"), n=-1))#the insertions are all done. #prints the last word added
    print(paste("number of words", as.character(rownum)))#number of words that were added.
  }
}

NamesRevsPurpsDB <- function(url, purpose, db){
  df <- NamesRevsPurps(url, purpose)#makes df out of list of links in url page
  for (row in 1:nrow(df[2])){
    thisname <- df$names[row] #name of doc
    thisrev <- as.character(df$revisions[row]) #revision of doc
    thispurp <- df$purpose[row]#this column is not different for each row
    thislang <- df$langs[row]#the lang of doc
    tryCatch({
      thiscontent <- wikiProcess(thisrev, thislang, db) #gets text from wikipage. throws error if text cant be retrieved
      putIntoDB(name = thisname, rev = thisrev, purp=thispurp, content=thiscontent, lang=thislang, mydb = db)#puts wikipage content into DB according to name, revision, purpose, and language
      print(paste(thisname, thisrev, "logged into DB."))#the document has been completely logged into db, according to prev. function.
    }, error = function(e) {
      if (e$message == "xpath"){ #other errors won't be handled 
        putIntoDB(name = thisname, rev = thisrev, purp=thispurp, lang=thislang, mydb = db, error = T)#allows for logging of xpath error into db
        warning(sprintf("xpath unable to retrieve content for %s %s ; error logged in database", thisname, thisrev))
      } else{
        stop(e$message)
      }
    })
  }
}

tfidfCSV <- function(url, purpose, username, pw){
  db <- dbConnect(MySQL(), user=username, password=pw, dbname='lexicon', host='localhost', port=3307)
  NamesRevsPurpsDB(url, purpose, db) #gets wikipedia content and puts it into database
  
  df<- fetch(dbSendQuery(db, paste0("select * from docs where purpose = \"",purpose,"\" and exists_error = 0 order by doc_id desc limit 100")), n=-1) #gets all documents just inserted, in order to do tfidf
  for(i in 1:nrow(df)){
    doc_id <- df[i,1]
    name <- df[i,2]
    rev <- df[i,4]
    purp <-df[i,5]
    tfidf <- fetch(dbSendQuery(db, paste0("call tfidf(",doc_id,")")), n=-1)#procedure now also includes exists_error DONE
    #worth mentioning lots of times, that integers like doc_id max out at around 2 billion in MySQL. The highest ints so far are ~900 million
    while(dbMoreResults(db)){
      res1<- dbNextResult(db)
      unneededresult <- fetch(res1, n=-1)#throwaway
    }
    write.csv(tfidf, paste(name,rev,paste0(purp,".csv"), sep="-"))#writes tfidf csv to working directory
  }
  dbDisconnect(db)#remember to disconnect db! there may only be 16 connections at once
}
