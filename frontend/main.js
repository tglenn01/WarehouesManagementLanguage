const consoleInput = document.querySelector(".console-input");
const historyContainer = document.querySelector(".console-history");
let input_number = 0;
let total_string = "";

// TODO The local save file function has security issues so it might not work on every brower

function evaluate(inputAsString, output){
    var outputAsString;
    if (output === "") {
      outputAsString = "Empty Input. Discarded";
    }
    else{
      outputAsString =
      output instanceof Array ? `[${output.join(", ")}]` : output.toString();
      total_string = total_string + outputAsString + '\n';
      outputAsString = "Added (" + outputAsString + ") to the " + document.getElementById("filename").value + ".txt file."
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
    consoleInput.remove();
    consoleInput = document.querySelector(".console-input");
    historyContainer = document.querySelector(".console-history");
  }
  else{

  }
}

function downloadFile(filename, content){
  alert(filename.length);
  if (input_number == 0){
    alert("No Robotcode command entered yet!");
    return;
  }
  const blob = new Blob([content], {
    type: 'plain/text'
  });
  const fileurl = URL.createObjectURL(blob);
  const element = document.createElement("a");
  element.setAttribute('href', fileurl);
  element.setAttribute('download', filename);
  element.click();
}

const download_button = document.getElementById("download");
download_button.addEventListener("click", function(){ 
  tmp_name = document.getElementById("filename").value;
  if (tmp_name.length == 0) {
    alert("No file name yet!");
    return;
  }
  downloadFile(tmp_name + '.txt', total_string);
});


consoleInput.addEventListener("keyup", (e) => {
  const code = consoleInput.value.trim();


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
