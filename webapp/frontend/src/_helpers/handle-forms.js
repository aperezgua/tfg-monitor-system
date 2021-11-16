export function handleMapData(formDataFromServer) {

     for (var key in formDataFromServer) {
                 console.log("DATA: "+key + " -- " + formDataFromServer[key]);
     }

    
    return formDataFromServer; 
}
