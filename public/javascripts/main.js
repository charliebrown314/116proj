function httpGet(url)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}
function addUserForm(name, profession) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "addUser?name=" + name +"&profession=" + profession, false);
    xmlHttp.send( null );
    console.log(xmlHttp.responseText);
    return xmlHttp.responseText;
}
function checkedRadio(buttonList){
    var val;
    for(var i = 0; i++; i < buttonList.length){
        if (buttonList[i].checked){
            val = buttonList[i].value;
        }
    }
    return val;
}