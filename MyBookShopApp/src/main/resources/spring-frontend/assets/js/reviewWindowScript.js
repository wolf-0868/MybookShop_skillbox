const openBtn = document.getElementById("open-review-dialog-btn");

//Query for dialog element
const dialog = document.getElementById("review-dialog");

//Add event listener to open dialog on click
openBtn.addEventListener("click", () => {
    dialog.showModal();
});
