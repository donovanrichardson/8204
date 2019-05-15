var hierarchy = [["z", "j"], ["q", "k"], ["x", "s"], ["j", "d"], ["k", "g"], ["v", "b"], ["f", "p"], ["w", "u"], ["y", "i"], ["m", "b"], ["c", "g"], ["l", "d"], ["p", "b"], ["u", "o"], ["h", "a"], ["r", "o"], ["s", "d"], ["n", "d"], ["t", "d"], ["i", "e"], ["o", "a"], ["g", "d"], ["e", "a"], ["d", "b"]];
function babyhelper(t, level) {
    console.log('begins to convert!')
    var helping = t;
    var helped = "";
    var curlevel;
    var curindex;
    for(curlevel = 0; curlevel < level; curlevel++) {
        helping = decouple(helping);
        helped = "";
        for(curindex = 0; curindex < helping.length;curindex++){
            if(helping.charAt(curindex) == hierarchy[curlevel][0]){
                helped += hierarchy[curlevel][1];
            } else {
                helped += helping.charAt(curindex);
            }
        }
        helped = decouple(helped);
        helping = helped;
        
    }
    console.log(helped);
    return helped;
}

//lol i guess t could be empty
function decouple(t) {
    console.log('decouples!')
    t = t.toLowerCase();
    var decouplized = "";
    var cur;
    decouplized += t.charAt(0)
    for (cur = 1; cur < t.length;cur++){
        if(decouplized.charAt(decouplized.length-1) === t.charAt(cur)) {
            continue
        } else {
            decouplized += t.charAt(cur)
        }
    }
    console.log(decouplized);
    return decouplized;
}

function babify(){
    
    var text = document.getElementById("textcontent").value;
    var level = document.getElementById("babylevel").value;

    var babified;
    
    babified = babyhelper(text, level);
    console.log(babified);
    
    document.getElementById("babifiedText").innerHTML = babified;
    
}