// for modal label
var labelBtnOpen = document.getElementsByClassName("labelTag")
var labelModal = document.querySelector('#labelModal');
var labelFooterClose = labelModal.querySelector('.close');
const labelDerections = [...labelModal.querySelectorAll('.a')];

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
        //console.log(value)
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
