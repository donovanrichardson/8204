// const time = document.createElement('h1');

// var today = new Date();
// var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
// var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
// var dateTime = date+' '+time;

// var boddy = document.getElementsByTagName('body');

reset = function() {
    while(window.canvas.firstChild){
      window.canvas.removeChild(window.canvas.firstChild);
    }
  }

  var getStops = new XMLHttpRequest();

  window.abbreviations = {"Amagansett":"AMG","Atlantic Terminal":"ATL","Babylon":"BAB","Cold Spring Harbor":"CSH","Far Rockaway":"FRK","Farmingdale":"FRM","Freeport":"FRE","Great Neck":"GRN","Greenport":"GPT","Hampton Bays":"HMB","Hempstead":"HEM","Hicksville":"HIC","Hunterspoint Avenue":"HPT","Huntington":"HTN","Jamaica":"JAM","Long Beach":"LBE","Long Island City":"LIC","Massapequa Park":"MPP","Mineola":"MIN","Montauk":"MTK","Oyster Bay":"OYS","Patchogue":"PAT","Penn Station":"PEN","Port Jefferson":"PTJ","Port Washington":"PTW","Ronkonkoma":"RON","Seaford":"SEA","Speonk":"SPE","Valley Stream":"VST","Wantagh":"WAN","West Hempstead":"WHE","Westhampton":"WHA","Yaphank":"YAP"}
  
  getStops.open('GET', 'http://localhost:8080/lirr/stops', true);

  getStops.onload = function(){
    this.data = JSON.parse(this.response);
    console.log("received")
    // document.addEventListener('DOMContentLoaded', (event) => {
      console.log(document.readyState);
      console.log(document.childNodes[1].childNodes);
      // window.canvas = document.getElementById("bd");
      console.log('DOM fully loaded and parsed');
      showStops();
      
  // });
  }
  
  window.titles = [];
  window.monthNames = ["January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];

  getStops.send();
  
  function maketitle(text){
    var title = document.createElement("h1");
    title.textContent = text;
    title.style.textAlign = "center";
    window.titles.push(title);
    document.getElementById("root").insertBefore(title, window.canvas);
  }
  /*
  function fxn() {
    var outer = document.getElementById('root'); 
    window.canvas = document.createElement('div');
    window.canvas.setAttribute('class', 'container');
    outer.appendChild(window.canvas);
    maketitle("Timetable");
    maketitle("Select Origin");
    // getRoute.send();
    getStops.data.forEach(stop => {
      const rCard = document.createElement('div');
      rCard.routeColor = `#${route.route_color}`;
      rCard.routeTextColor = `#${route.route_text_color}`;
      rCard.setAttribute('class', 'card');
      rCard.addEventListener("click", function(){    
        window.routeId = route.route_id;
        window.routeColor = rCard.routeColor;
        window.routeTextColor = rCard.routeTextColor;
        getStops = new XMLHttpRequest();
        getStops.open('GET', `https://deparch.herokuapp.com/boston/stops?routeId=${window.routeId}`, true);
        getStops.onload = function(){
          this.data = JSON.parse(this.response);
          console.log("received");
          // console.log(this.data);
          showStops(this.data);
        // });
        }
        getStops.send()
      })

        
  
      
      const h1 = document.createElement('h1');
      h1.textContent = route.default_name;
      
      h1.style.backgroundColor = rCard.routeColor; //hmm, this is not working, figur it out later.
      h1.style.color = rCard.routeTextColor;
  
      
  
      const p = document.createElement('p');
      // route.description = route.long_name;
      // p.textContent = route.long_name;
      p.textContent = route.route_long_name;
  
      window.canvas.appendChild(rCard);
      rCard.appendChild(h1);
      rCard.appendChild(p);
    });
  }
  */
  showStops = function (){
    maketitle("Timetable");
    maketitle("Select Origin");
    var outer = document.getElementById('root'); 
    window.canvas = document.createElement('div');
    outer.appendChild(window.canvas);
    reset();
    // window.titles[1].textContent="Select Origin";
    getStops.data.forEach(stop => {
      const sCard = document.createElement('div');
      sCard.setAttribute('class', 'card');
      sCard.addEventListener("click", function(){    
        window.origin = stop.stop_id;
        showDests();
      })
  
      const h1 = document.createElement('h1');
      h1.textContent = stop.stop_name;
      // h1.style.backgroundColor = route.route_color; //hmm, this is not working, figur it out later.
      // h1.style.textColor = route.route_text_color;
  
      // const p = document.createElement('p');
      // // route.description = route.long_name;
      // // p.textContent = route.long_name;
      // p.textContent = route.route_long_name;
  
      window.canvas.appendChild(sCard);
      sCard.appendChild(h1);
      // rCard.appendChild(p);
    });
  
  }
  
  showDests = function(){
    reset();
    window.titles[1].textContent="Select Direction";
    // getTimetable = function(){   
    //     window.testamundo
    //     window.direction = num;
    //     getTT = new XMLHttpRequest();
    //     window.todayDate = new Date()
    //     getTT.open('GET', `localhost:8080/lirr/timetable?origin=${window.origin}&year=${window.todayDate.getFullYear()}&month=${window.todayDate.getMonth()+1}&date=${window.todayDate.getDate()}`, true);
    //     getTT.onload = function(){
    //       this.data = JSON.parse(this.response);
    //       console.log("received");
    //       console.log(this.data);
    //       showTT(this.data);
    //     // });
    //     }
    //     getTT.send()
    //   }
    getTT = new XMLHttpRequest();
        window.todayDate = new Date()
        
        getTT.onload = function(){
          this.data = JSON.parse(this.response);
          console.log("received");
          console.log(this.data);
          showTT(this.data);
        // });
        }
        req = `http://localhost:8080/lirr/timetable?origin=${window.origin}&year=${window.todayDate.getFullYear()}&month=${window.todayDate.getMonth()+1}&date=${window.todayDate.getDate()}`
      wbCard = document.createElement('div');
      wbCard.setAttribute('class', 'card');
      wbCard.addEventListener("click", function(){ 
        
        getTT.open('GET',req , true);
        window.direction = 1;
        
        getTT.send()
      });
      ebCard = document.createElement('div');
      ebCard.setAttribute('class', 'card');
      ebCard.addEventListener("click", function(){ 
        getTT.open('GET', req, true);  
        window.direction = 0;
        // getTT = new XMLHttpRequest();
        // window.todayDate = new Date()
        // getTT.open('GET', `localhost:8080/lirr/timetable?origin=${window.origin}&year=${window.todayDate.getFullYear()}&month=${window.todayDate.getMonth()+1}&date=${window.todayDate.getDate()}`, true);
        // getTT.onload = function(){
        //   this.data = JSON.parse(this.response);
        //   console.log("received");
        //   console.log(this.data);
        //   showTT(this.data);
        // });
        
        getTT.send()}
      );

      const hw = document.createElement('h1');
      hw.textContent = "Westbound";
      const he = document.createElement('h1');
      he.textContent = "Eastbound";
      window.canvas.appendChild(wbCard);
      window.canvas.appendChild(ebCard);
      wbCard.appendChild(hw);
      ebCard.appendChild(he);
    }


  
  showTT = function(json){
    reset();
    document.getElementById("root").childNodes.forEach(cnode =>{
      if (cnode.id != "form") {document.getElementById("root").removeChild(cnode);}
    })
    var from = json.origin.stop_name;
    var table = document.createElement("table");
    document.getElementById("root").appendChild(window.canvas);
    window.canvas.appendChild(table);
    var title = document.createElement("tr");
    var titleContent = document.createElement("td");
    titleContent.colSpan=2;
    var row1 = document.createElement('p');
    var row2 = document.createElement('p');
    var direction = window.direction ? "Westbound" : "Eastbound"
    row2.textContent = `${direction} Departures from ${from}`
    var row3 = document.createElement('p');
    row3.textContent = timecard(window.todayDate);
    //i maybe shouldnt be using routeId in the line above bc in some systems it isn't a helpful identifier, but i'm keeping it
    title.style.textAlign = "center";
    table.appendChild(title);
    title.appendChild(titleContent);
    titleContent.appendChild(row1);
    titleContent.appendChild(row2);
    titleContent.appendChild(row3);
    var shaded = false;
    json.result.forEach(time =>{
      var tObj = {h : time.departure_time.substr(0,2), m : time.departure_time.substr(3,2), dir : time.westbound};
      if(tObj.dir != window.direction){
          return;
      }
      if (!document.getElementById(tObj.h)){
        var hrow = document.createElement("tr");
        hrow.setAttribute("id", tObj.h);
        var hnum = document.createElement("td");
        hnum.setAttribute("class", "hour");
        hnum.textContent = tObj.h;
        var mnum = document.createElement("td");
        mnum.setAttribute("class", "minute");
        window.rowTable = document.createElement("table");
        window.rowTable.style.textAlign = "center";
        window.topRow = document.createElement("tr");
        window.botRow = document.createElement("tr");
        var minuteCell = document.createElement("td");
        var termCell = document.createElement("td");
        minuteCell.textContent = tObj.m;
        minuteCell.style.padding = "15px"
        termCell.textContent = window.abbreviations[time.terminus];

        window.topRow.appendChild(minuteCell);
        window.botRow.appendChild(termCell);
        window.rowTable.appendChild(topRow);
        window.rowTable.appendChild(botRow);
        mnum.appendChild(rowTable);
        
        // mnum.textContent = tObj.m;
        table.appendChild(hrow);
        hrow.appendChild(hnum);
        hrow.appendChild(mnum);
        
        if (shaded){
          hnum.style.backgroundColor = `#0039A644`;
        //   hnum.style.color = window.routeTextColor; might change to white in the future
          mnum.style.backgroundColor = `#0039A644`;
        }
        // else{
        //   hrow.style.backgroundColor = "#cccccc"
        // }
        shaded = !shaded;
  
      }else{
        // var row = document.getElementById(tObj.h);
        // row.children[1].textContent += `, ${tObj.m}`
        var minuteCell = document.createElement("td");
        var termCell = document.createElement("td");
        minuteCell.textContent = tObj.m;
        minuteCell.style.padding = "0px 15px"
        minuteCell.style.margin = "0px"
        // minuteCell.style.padding = "0px 15px"
        termCell.textContent = window.abbreviations[time.terminus];

        window.topRow.appendChild(minuteCell);
        window.botRow.appendChild(termCell); //literally copypasted, so not really necessary to have in an else
      }
    })
    var footerRow = document.createElement("tr");
    var footer = document.createElement("td");
    footer.colSpan = 2;
    footer.textContent = "Timetable created from GTFS data provided by the New York MTA. \n The author of this web app is not affiliated with the MTA.";
    footer.style.fontSize = "14px";
    table.appendChild(footerRow);
    footerRow.appendChild(footer);
  
    var form = document.getElementById("form");
    form.style.display = "block";
  
      var submit = document.getElementById("submit");
      var select =  document.getElementById("select");
        // var root = document.getElementById("root");
  
        // submit.setAttribute("id", "submit");
        // submit.setAttribute("type", "submit");
        // submit.setAttribute("value", "Submit");
  
        submit.addEventListener("click", function(){
          day = document.getElementById("day").value;
        month = select.selectedIndex;
        year = document.getElementById("year").value;
          if (day > 31 || day < 1 ){
            alert("day must be in between 1 and 31")
            return;
          } else if (year > 9999 || year < 1){
            alert("year must be in between 1 and 9999")
            return;
          } else{
            window.todayDate = new Date(year, month, day);
            getTT.open('GET', `https://deparch.herokuapp.com/boston/timetable?routeId=${window.routeId}&origin=${window.origin}&dest=${window.dest}&year=${window.todayDate.getFullYear()}&month=${window.todayDate.getMonth()+1}&date=${window.todayDate.getDate()}`, true);
            getTT.send();
          }
        })
  
  
  }
  
  
  
  
  
  function timecard(oneday){
  
    const nowdiv = document.createElement('div');
    nowdiv.setAttribute('class', 'card');
  
    const weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
  
  return `${weekdays[oneday.getDay()]}, ${window.monthNames[oneday.getMonth()]} ${oneday.getDate()}, ${oneday.getFullYear()}`
    // var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
    // var fulldatetime = weekdays[today.getDay()] +', ' + monthNames[today.getMonth()+1] + " " +today.getDate() +', ' + today.getFullYear() + "\n" + today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
  
    // const nowShort = document.createElement('h1');
    // nowShort.textContent = date;
  
    // const nowLong = document.createElement('p');
    // nowLong.textContent = fulldatetime;
  
    // // var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    // // var dateTime = date+' '+time;
    // window.boddy.appendChild(nowdiv);
    // nowdiv.appendChild(nowShort);
    // nowdiv.appendChild(nowLong);
  
  }
  
  // time.textContent = dateTime;
  // alert("hiii");

