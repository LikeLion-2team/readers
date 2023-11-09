let postBoard = {
    init: function () {
        let _this = this;
        $("#btn-save").on("click", function () {
            _this.saveBoard();
        });
        $("#btn-update").on("click", function () {
            _this.updateBoard();
        });
        $("#btn-delete").on("click", function () {
            _this.deleteBoard();
        });
    },
    saveBoard: function () {

        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };
        if(data.title == ''){
            alert('제목을 입력하세요');
            return;
        }
        if(data.content == ''){
            alert('내용을 입력하세요');
            return;
        }

        console.log(data);

        $.ajax({
            type: "POST"
            , url: "/board/save"
            , dataType: "json"
            , contentType: "application/json; charset=utf-8"
            , data: JSON.stringify(data)
        })
            .done(function () {
                alert("등록되었습니다.");
                window.location.href = "/board/list/0"
            }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },
    updateBoard: function () {
        let boardNumVal = $("#id").val();
        if (isNaN(boardNumVal)) {
            alert('게시판 번호가 올바르지 않습니다.');
            return;
        }

        let id = parseInt($("#id").val(), 10);
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };
        if(data.title == ''){
            alert('제목을 입력하세요');
            return;
        }
        if(data.content == ''){
            alert('내용을 입력하세요');
            return;
        }
        alert("수정되었습니다.")
        console.log(data);

        $.ajax({
            type: "PUT"
            , url: "/board/update/" + id
            , dataType: "json"
            , contentType: "application/json; charset=utf-8"
            , data: JSON.stringify(data)
        })
            .done(function (res) {
                if (res.msg) {
                    alert(res.msg);
                } else {
                    alert('수정되었습니다.');
                    location.href = "/board/list/0";
                }
            })
            .fail(function (error) {
                alert(JSON.stringify(error));
            })
    },
    deleteBoard: function () {
        let boardNumVal = parseInt($("#id").val(), 10);
        if (isNaN(boardNumVal)) {
            alert('게시판 번호가 올바르지 않습니다.');
            return;
        }
        let id = boardNumVal;

        $.ajax({
            type: "DELETE"
            , url: "/board/delete/" + id
            , dataType: "json"
            , contentType: "application/json; charset=utf-8"
        })
            .done(function (res) {
                if (res.msg) {
                    alert(res.msg);
                } else {
                    alert('삭제되었습니다.');
                    location.href = "/board/list/0";
                }
            })
            .fail(function (error) {
                alert(JSON.stringify(error));
            })
    }
}

postBoard.init();