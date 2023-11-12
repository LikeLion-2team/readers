let postReply = {
    init: function () {
        let _this = this;
        $("#replyBtn-save").on("click", function () {
            _this.saveReply();
        });
        $("#replyBtn-update").on("click", function () {
            _this.updateReply();
        });
        $("#replyBtn-delete").on("click", function () {
            _this.deleteReply();
        });
    },
    saveReply: function () {

        let data = {
            content: $("#rplContent").val(),
            boardNum: parseInt(window.location.href.split('/').pop())
        };
        if(data.content ==''){
            alert('내용을 입력하세요');
            return;
        }

        let url = window.location.href;
        let boardNum = url.split('/').pop();
        console.log(data);

        $.ajax({
            type: "POST"
            , url: "/replies/save"
            , dataType: "json"
            , contentType: "application/json; charset=utf-8"
            , data: JSON.stringify(data)
        })
            .done(function () {
                alert("등록되었습니다.");
                window.location.href = "/replies/list/"+boardNum;
            }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    updateReply: function () {
        let rplNumVal = $("#id").val();
        if (isNaN(rplNumVal)) {
            alert('댓글 번호가 올바르지 않습니다.');
            return;
        }

        let url = window.location.href;
        let boardNum = url.split('/').pop();
        let data = {
            content: $("#rplContent").val()
        };

        if(data.content == ''){
            alert('내용을 입력하세요');
            return;
        }
        alert("수정되었습니다.")
        console.log(data);

        $.ajax({
            type: "PUT"
            , url: "/replies/update/" + rplNumVal   // 수정할 댓글 번호를 URL에 포함
            , dataType: "json"
            , contentType: "application/json; charset=utf-8"
            , data: JSON.stringify(data)
        })
            .done(function (res) {
                if (res.msg) {
                    alert(res.msg);
                } else {
                    alert('수정되었습니다.');
                    location.href = "/replies/list/" + boardNum;
                }
            })
            .fail(function (error) {
                alert(JSON.stringify(error));
            })
    },

    deleteReply: function () {
        let rplNumVal = parseInt($("#id").val(), 10);
        if (isNaN(rplNumVal)) {
            alert('댓글 번호가 올바르지 않습니다.');
            return;
        }
        let id = rplNumVal;
        let boardNum = parseInt($("#boardNum").val(), 10);

        $.ajax({
            type: "DELETE"
            , url: "/replies/delete/" + id
            , dataType: "json"
            , contentType: "application/json; charset=utf-8"
        })
            .done(function (res) {
                if (res.msg) {
                    alert(res.msg);
                } else {
                    alert('삭제되었습니다.');
                    location.href = "/replies/list/"+ boardNum;
                }
            })
            .fail(function (error) {
                alert(JSON.stringify(error));
            })
    }
}

postReply.init();