function ajaxPost(){

	var blockList = new Array();

	var blocks = document.querySelectorAll('.block');

    var documentTitle = document.querySelector('.title').value;
    if (documentTitle === ""){
        documentTitle = '제목 없음';
    }
    var documentId = document.querySelector('.documentId').getAttribute('value');

	for(var i=0; i < blocks.length; i++){

    			var data = new Object();

    			data.category = blocks.item(i).getAttribute('id');
    			data.index = i;
    			if (data.category === 'blockImage' || data.category === 'blockVideo'){
    			    data.content = blocks.item(i).getAttribute('content');
    			}
    			else{
    			    data.content = blocks.item(i).innerHTML;
    			}
    			blockList.push(data) ;
    }

    var jsonData = JSON.stringify({title: documentTitle, id: documentId, blockList: blockList});

    alert(jsonData);

	$.ajax({
        url: "/document/save",
        type: "POST",
        dataType: "json",
        contentType: 'application/json',
        data: jsonData,

        success:
        function(data){
            alert("저장 성공");
        },
        error:
        function(){
            alert("저장 실패");
        }
    });
}

$(document).ready(function(){
    var sortable_element = $('.sortable');
    sortable_element.sortable(
    {
        items: ".move_block",
        handle: ".move",
        cursor: "move",
        opacity: 0.7,
        containment: ".sortable"
    });
});

function createMoveBlock(new_block){
    let tagArea = document.querySelector('.sortable');

    let new_move_block = document.createElement('div');
    new_move_block.setAttribute('class', 'move_block');

    let move = document.createElement('div');
    move.setAttribute('class', 'move');
    move.innerText = '+';

    new_move_block.appendChild(move);
    new_move_block.appendChild(new_block);

    tagArea.appendChild(new_move_block);
}

function createBlockH1(){
    let new_block = document.createElement('div');

    new_block.setAttribute('id', 'blockH1');
    new_block.setAttribute('class', 'block');
    new_block.setAttribute('contenteditable', 'true');
    new_block.setAttribute('placeholder', '빈 블럭');
    new_move_block.setAttribute('spellcheck', 'false');

    createMoveBlock(new_block);
}

function createBlockH2(){
    let new_block = document.createElement('div');

    new_block.setAttribute('id', 'blockH2');
    new_block.setAttribute('class', 'block');
    new_block.setAttribute('contenteditable', 'true');
    new_block.setAttribute('placeholder', '빈 블럭');
    new_move_block.setAttribute('spellcheck', 'false');

    createMoveBlock(new_block)
}

function createBlockH3(){
    let new_block = document.createElement('div');

    new_block.setAttribute('id', 'blockH3');
    new_block.setAttribute('class', 'block');
    new_block.setAttribute('contenteditable', 'true');
    new_block.setAttribute('placeholder', '빈 블럭');
    new_move_block.setAttribute('spellcheck', 'false');

    createMoveBlock(new_block);
}

function createBlockP(){
    let new_block = document.createElement('div');

    new_block.setAttribute('id', 'blockP');
    new_block.setAttribute('class', 'block');
    new_block.setAttribute('contenteditable', 'true');
    new_block.setAttribute('placeholder', '빈 블럭');
    new_move_block.setAttribute('spellcheck', 'false');

    createMoveBlock(new_block)
}

