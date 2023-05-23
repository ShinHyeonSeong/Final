/* 문서저장 */
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

    /*alert(jsonData);*/

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

/* sortable */
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

/* 블럭 생성 */
function createBlock(block_id){
    let new_block = document.createElement('div');

    new_block.setAttribute('id', block_id);
    new_block.setAttribute('class', 'block');
    new_block.setAttribute('contenteditable', 'true');
    new_block.setAttribute('placeholder', '빈 블럭');
    new_block.setAttribute('spellcheck', 'false');

    createMoveBlock(new_block);
}

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

/* 블럭 생성 연결 함수 */
function createBlockH1(){
    createBlock('blockH1');
}

function createBlockH2(){
    createBlock('blockH2');
}

function createBlockH3(){
    createBlock('blockH3');
}

function createBlockP(){
    createBlock('blockP');
}

/* 단축키 설정 */

//ctrl + s
$(document).keydown(function(e) {
    if((e.which == '115' || e.which == '83') && (e.ctrlKey || e.metaKey)) {
        e.preventDefault();
        ajaxPost();
    }
});

/* context 메뉴 */