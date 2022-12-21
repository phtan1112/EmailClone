// needed for send get request
const emailArchive = document.getElementById("emailArchive")
const btnSendEmail = document.getElementById("btnSendEmail")
const btnCloseEmail = document.getElementById("btnCloseEmail")

btnCloseEmail.onclick = e => emailArchive.value = "DRAFT"
btnSendEmail.onclick = e => emailArchive.value = "INBOX"
//

function openForm() {
    closeFormEdit();
    closeFormChangePassword();
    closeFormBlock();
    document.getElementById("popupInfo").style.display = "block";
}
function closeForm() {
    document.getElementById("popupInfo").style.display = "none";
}




function openFormEdit() {
    closeForm();
    closeFormChangePassword()
    closeFormBlock();
    document.getElementById("popupEdit").style.display = "block";
}
function closeFormEdit() {
    document.getElementById("popupEdit").style.display = "none";
}


function openFormChangePassword() {
    closeForm();
    closeFormEdit();
    closeFormBlock()
    document.getElementById("popupChangePassword").style.display = "block";
}
function closeFormChangePassword() {
    document.getElementById("popupChangePassword").style.display = "none";
}


function openFormBlock() {
    closeForm();
    closeFormEdit();
    closeFormChangePassword()
    document.getElementById("popupBlock").style.display = "block";
}
function closeFormBlock() {
    document.getElementById("popupBlock").style.display = "none";
}


var btnOpen = document.querySelector('.modal__open');
var modal = document.querySelector('.modal');
var footerClose = document.querySelector('.close');
const derections = [...document.querySelectorAll('.a')];
const starActive = document.getElementsByClassName('starActive');

derections.forEach(derection  => {
    derection.addEventListener('click', (e) => {
        derections.forEach(item => item.classList.remove("active"));
        e.target.classList.add("active");
    })
})

// needed for send get request
console.log(starActive)
for (var i = 0; i < starActive.length; i++){
    const star = starActive[i]
    const starAnchor = star.children[0]
    starActive[i].addEventListener('click', (e) => {
        if(e.target.style.color) {
            e.target.style.color = ''
        }
        else {
            e.target.style.color = 'yellow'
        }
        console.log(star)
        console.log(starAnchor)
        console.log(starAnchor.href)
        var xhr = new XMLHttpRequest();
        xhr.open('GET', starAnchor.href, true);
        xhr.send();
    })
}


// for modal label
var labelBtnOpen = document.getElementsByClassName("labelTag")
var labelModal = document.querySelector('#labelModal');
var labelFooterClose = labelModal.querySelector('.close');
const labelDerections = [...labelModal.querySelectorAll('.a')];
const eyes = document.getElementsByClassName("fa-eye")
var labels = document.getElementsByClassName("labelAnchor")


labelDerections.forEach(derection  => {
    derection.addEventListener('click', (e) => {
        derections.forEach(item => item.classList.remove("active"));
        e.target.classList.add("active");
    })
})

function replaceEmailIdForLabels(newId){
    let id = "email="+newId
    for(let i=0; i < labels.length; ++i){
        let start = labels[i].href.indexOf("?")
        let end = labels[i].href.indexOf("&")
        let old = labels[i].href.substring(start+1, end)
        labels[i].href = labels[i].href.replace(old, id)
        console.log(labels[i].href)
    }
}

function toggleLabelModal(){
    labelModal.classList.toggle('hide')
}

for (let i=0; i<labelBtnOpen.length; ++i){
    labelBtnOpen[i].addEventListener('click', (e) => {
        toggleLabelModal();
        let strHref = labelBtnOpen[i].previousElementSibling.href
        let start = strHref.indexOf("=")
        let end = strHref.indexOf("&")
        let value = strHref.substring(start+1, end)
        console.log(value)
        replaceEmailIdForLabels(value)
    })
}

labelModal.addEventListener('click', (e) => {
    if(e.target == e.currentTarget){
        toggleLabelModal();
    }

});
labelFooterClose.addEventListener('click', (e) => {
    toggleLabelModal();
});

//////////////////

function togleModal (){
    modal.classList.toggle('hide');
}
console.log(btnOpen)

btnOpen.addEventListener('click', (e) => {
    togleModal();
});
modal.addEventListener('click', (e) => {
    if(e.target == e.currentTarget){
        togleModal();
    }

});
footerClose.addEventListener('click', (e) => {
    togleModal();
});

function addLabel() {
    closeFormEdit();
    closeFormChangePassword();
    closeFormBlock();
    closeForm();
    document.getElementById("popupAddLabel").style.display = "block";
}
function closeLabel() {
    document.getElementById("popupAddLabel").style.display = "none";
}

function editLabel(id, name) {
    closeFormEdit();
    closeFormChangePassword();
    closeFormBlock();
    closeForm();
    closeLabel();
    closeDeleteLabel();
    document.getElementById("popupEditLabel").style.display = "block";
    document.getElementById("newEditLabelName").value = name;
    document.getElementById("labelId").value = id;
}
function closeEditLabel() {
    document.getElementById("popupEditLabel").style.display = "none";
}

function deleteLabel(id) {
    closeFormEdit();
    closeFormChangePassword();
    closeFormBlock();
    closeForm();
    closeLabel();
    closeEditLabel();
    document.getElementById("popupDeleteLabel").style.display = "block";
    document.getElementById("deleteLabelId").value = id;
}
function closeDeleteLabel() {
    document.getElementById("popupDeleteLabel").style.display = "none";
}

