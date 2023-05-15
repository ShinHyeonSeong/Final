const fileInput = document.getElementById("fileInput");

fileInput.addEventListener('change',function(e){

    var files = e.target.files;

    var formData = new FormData();

    formData.append('uploadFile', files[0]);

    $.ajax({
            url: "/fileUpload",
            type: "POST",
            contentType: false,
            processData: false,
            data: formData,
            success:
            function(result){
                alert("저장 성공");
                getImage();
            }
    });
});

function getImage(){
    $.ajax({
        type: 'GET',
        url: '/fileName/return',
        dataType: 'text',
        success: function(result) {
            makeImg(result);
        }
    });
}

function makeImg(imageName){
    let tagArea = document.querySelector('.content');

    let new_block = document.createElement('div');

    new_block.setAttribute('id', 'blockImage');
    new_block.setAttribute('class', 'block');
    new_block.setAttribute('content', imageName);

    let imageEl = document.createElement('img');

    imageEl.setAttribute('src', imageName);

    new_block.appendChild(imageEl);

    tagArea.appendChild(new_block);
}

const upload = document.getElementById("image-btn");

upload.addEventListener('click', () => fileInput.click());