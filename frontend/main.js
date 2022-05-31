const consoleInput = document.querySelector(".console-input");
const historyContainer = document.querySelector(".console-history");
let input_number = 0;

document.getElementById('file').onchange = function(){
    var file = this.files[0];
    var reader = new FileReader();
    reader.onload = function(progressEvent){
      console.log(this.result);
    };
    reader.readAsText(file);
  };

function evaluate(inputAsString, output){
    var outputAsString;
    if (output === "") {
      outputAsString = "Empty Input. Discarded";
    }
    else{
      outputAsString =
      output instanceof Array ? `[${output.join(", ")}]` : output.toString();
      outputAsString = "Added (" + outputAsString + ") to the .txt file."
      input_number++;
    }
    const inputLogElement = document.createElement("div");
    const outputLogElement = document.createElement("div");

    inputLogElement.classList.add("console-input-log");
    outputLogElement.classList.add("console-output-log");

    inputLogElement.textContent = `> ${inputAsString}`;
    outputLogElement.textContent = outputAsString;

    historyContainer.append(inputLogElement, outputLogElement);
}

function removeLog(){
  if (confirm("Do you want to delete all the previous inputs?") == true){
    historyContainer.remove();
  }
  else{
    return;
  }
}


consoleInput.addEventListener("keyup", (e) => {
  const code = consoleInput.value.trim();

  // if (code.length === 0) {
  //   return;
  // }

  let dynamic_mode = 0;

  if (e.key === "Enter") {
    const inputAsString = consoleInput.value.trim();
    if (dynamic_mode == 0){
        evaluate(inputAsString, inputAsString);
    }
    else{

    }
    consoleInput.value = "";
    historyContainer.scrollTop = historyContainer.scrollHeight;
  }
   if (e.key === "Backspace" && code.length === 0 && input_number != 0){
     removeLog();
   }
});
