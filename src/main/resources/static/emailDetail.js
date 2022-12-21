// needed for send get request
const emailArchive = document.getElementById("emailArchive")
const btnSendEmail = document.getElementById("btnSendEmail")
const btnCloseEmail = document.getElementById("btnCloseEmail")

btnCloseEmail.onclick = e => emailArchive.value = "DRAFT"
btnSendEmail.onclick = e => emailArchive.value = "INBOX"

// For reply
const replyEmailArchive = document.getElementById("replyEmailArchive")
const btnSendReply = document.getElementById("btnSendReply")
const btnCloseReply = document.getElementById("btnCloseReply")

btnCloseReply.onclick = e => emailArchive.value = "DRAFT"
btnSendReply.onclick = e => emailArchive.value = "INBOX"
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


    // Reply part
  var replyBtn = document.getElementById('replyBtn')
  var replyModal = document.querySelector('#replyModal');
  var replyFooterClose = replyModal.querySelector('.close');
  const replyDerections = [...replyModal.querySelectorAll('.a')];

  derections.forEach(derection  => {
    derection.addEventListener('click', (e) => {
      derections.forEach(item => item.classList.remove("active"));
      e.target.classList.add("active");
    })
  })

   replyDerections.forEach(derection  => {
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
  //

  function togleModal (){
    modal.classList.toggle('hide');
  }
  function toogleReplyModal(){
    replyModal.classList.toggle('hide');
  }

  console.log(btnOpen)

  replyBtn.addEventListener('click', (e) => {
     toogleReplyModal();
  });
  replyModal.addEventListener('click', (e) => {
      if(e.target == e.currentTarget){
        toogleReplyModal();
      }

    });
  replyFooterClose.addEventListener('click', (e) => {
    toogleReplyModal();
  });


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

