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

  var getRoute = new XMLHttpRequest();
  
  getRoute.open('GET', 'https://deparch.herokuapp.com/boston/routes', true);

  getRoute.onload = function(){
    this.data = JSON.parse(this.response);
    console.log("received")
    // document.addEventListener('DOMContentLoaded', (event) => {
      console.log(document.readyState);
      console.log(document.childNodes[1].childNodes);
      // window.canvas = document.getElementById("bd");
      console.log('DOM fully loaded and parsed');
      fxn();
  // });
  }


  getRoute.send();

  


// class XHRWapper extends XMLHttpRequest {
//   onload = function(){
//       this.data = JSON.parse(this.response);
//     }
// } actually, has to be a property and not part of the proto

window.titles = [];

function maketitle(text){
  var title = document.createElement("h1");
  title.textContent = text;
  title.style.textAlign = "center";
  window.titles.push(title);
  document.getElementById("root").insertBefore(title, window.canvas);
}

function fxn() {
  window.canvas = document.getElementById("container");
  maketitle("Timetable");
  maketitle("Select Route");
  // getRoute.send();
  getRoute.data.forEach(route => {
    const rCard = document.createElement('div');
    rCard.routeColor = `#${route.route_color}`;
    rCard.routeTextColor = `#${route.route_text_color}`;
    rCard.setAttribute('class', 'card');
    rCard.addEventListener("click", function(){    
      window.routeId = route.route_id;
      window.routeColor = rCard.routeColor;
      window.routeTextColor = rCard.routeTextColor;
      window.getStops = new XMLHttpRequest();
      window.getStops.open('GET', `https://deparch.herokuapp.com/boston/stops?routeId=${window.routeId}`, true);
      window.getStops.onload = function(){
        this.data = JSON.parse(this.response);
        console.log("received");
        // console.log(this.data);
        showStops(this.data);
      // });
      }
      window.getStops.send()
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

showStops = function (json){
  reset();
  window.titles[1].textContent="Select Origin";
  json.forEach(stop => {
    const sCard = document.createElement('div');
    sCard.setAttribute('class', 'card');
    sCard.addEventListener("click", function(){    
      window.origin = stop.stop_id;
      window.getDests = new XMLHttpRequest();
      window.getDests.open('GET', `https://deparch.herokuapp.com/boston/dests?routeId=${window.routeId}&origin=${window.origin}`, true);
      window.getDests.onload = function(){
        this.data = JSON.parse(this.response);
        console.log("received");
        // console.log(this.data);
        showDests(this.data);
      // });
      }
      window.getDests.send()
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

showDests = function (json){
  reset();
  window.titles[1].textContent="Select Destination";
  json.forEach(stop => {
    const sCard = document.createElement('div');
    sCard.setAttribute('class', 'card');
    sCard.addEventListener("click", function(){    
      window.dest = stop.stop_id;
      window.getTT = new XMLHttpRequest();
      window.todayDate = new Date()
      window.getTT.open('GET', `https://deparch.herokuapp.com/boston/timetable?routeId=${window.routeId}&origin=${window.origin}&dest=${window.dest}&year=${window.todayDate.getFullYear()}&month=${window.todayDate.getMonth()+1}&date=${window.todayDate.getDate()}`, true);
      window.getTT.onload = function(){
        this.data = JSON.parse(this.response);
        console.log("received");
        console.log(this.data);
        showTT(this.data);
      // });
      }
      window.getTT.send()
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

showTT = function(json){
  reset();
  window.titles.forEach(wintitle =>{
    document.getElementById("root").removeChild(wintitle);
  })
  var from = json.origin.stop_name;
  var to = json.destination.stop_name;
  var table = document.createElement("table");
  var p = document.createElement("p");
  p.textContent = "Timetable created from MBTA GTFS data provided by Massachusetts Department of Transportation"
  window.canvas.appendChild(table);
  window.canvas.appendChild(p);
  var title = document.createElement("tr");
  var titleContent = document.createElement("td");
  titleContent.colSpan=2;
  var row1 = document.createElement('p');
  row1.textContent = window.routeId;
  var row2 = document.createElement('p');
  row2.textContent = `Departures from ${from} to ${to}`
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
    var tObj = {h : time.departure_time.substr(0,2), m : time.departure_time.substr(3,2) };
    if (!document.getElementById(tObj.h)){
      var hrow = document.createElement("tr");
      hrow.setAttribute("id", tObj.h);
      var hnum = document.createElement("td");
      hnum.setAttribute("class", "hour");
      hnum.textContent = tObj.h;
      var mnum = document.createElement("td");
      mnum.setAttribute("class", "minute");
      mnum.textContent = tObj.m;
      table.appendChild(hrow);
      hrow.appendChild(hnum);
      hrow.appendChild(mnum);
      
      if (shaded){
        hnum.style.backgroundColor = window.routeColor;
        hnum.style.color = window.routeTextColor;
        mnum.style.backgroundColor = `${window.routeColor}44`
      }
      // else{
      //   hrow.style.backgroundColor = "#cccccc"
      // }
      shaded = !shaded;

    }else{
      var row = document.getElementById(tObj.h);
      row.children[1].textContent += `, ${tObj.m}`
    }
  })

}





function timecard(oneday){

  const nowdiv = document.createElement('div');
  nowdiv.setAttribute('class', 'card');

  const weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

  const monthNames = ["January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December"
];

return `${weekdays[oneday.getDay()]}, ${monthNames[oneday.getMonth()]} ${oneday.getDate()}, ${oneday.getFullYear()}`
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