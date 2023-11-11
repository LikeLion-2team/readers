$(() => {
	$("#btnCancel").click(() => {
		location.href = "/gallery/list/0";
	});

	$("#btnGalleryRegister").click(() => {
		//사용자 입력 데이터 검증
		if (!checkValidation()) {
			return;
		}

		let formData = new FormData();

		//입력 데이터 저장
		$.ajax({
			url: "/gallery/save",
			data: {
				title: $("#bookTitle").val(),
				author: $("#bookAuthor").val(),
				publisher: $("#bookPublisher").val()
			},
			dataType: "json",
			method: "post"
		}).done((res) => {
			alert("책을 등록했습니다.");
			let fileName = res.galleryNum;
			formData.append("file", $("#bookCoverImg")[0].files[0]);
			formData.append("fileName", fileName);
			//첨부 파일 저장
			$.ajax({
				url: "/gallery/imgUpload",
				data: formData,
				contentType: false,
				processData: false,
				method: "post"
			}).done((res) => {
				console.log('파일 업로드 성공');
			}).fail((res, error, status) => {
				console.log(error);
			});
			location.href = "/gallery/list/0";
		}).fail((res, error, status) => {
			console.error("오류 발생:", error, status);
		});
	});
});

function checkValidation() {
	if ($.trim($("#bookTitle").val()).length == 0) {
		alert('책 제목을 입력해주세요.');
		$("#bookTitle").focus();
		return false;
	}

	if ($.trim($("#bookAuthor").val()).length == 0) {
		alert('저자를 입력해주세요.');
		$("#bookAuthor").focus();
		return false;
	}

	if ($.trim($("#bookPublisher").val()).length == 0) {
		alert('출판사를 입력해주세요.');
		$("#bookPublisher").focus();
		return false;
	}

	let selectedFile = $("#bookCoverImg")[0].files[0];
	let infoMsg = '<span style="color: red;">파일을 첨부해주세요.</span>';
	if (selectedFile == null) {
		$("#bookCoverImg").parent().siblings('label').after(infoMsg);
		alert('이미지를 첨부해주세요.');
		$("#bookCoverImg").focus();
		return false;
	}

	selectedFile = $("#bookCoverImg")[0].files[0];
	let fileName = selectedFile.name;
	let allowedExtensions = ['.jpg', '.jpeg', '.png'];


	// 파일 확장자 검증
	if (!allowedExtensions.some(ext => fileName.endsWith(ext))) {
		alert('올바른 확장자가 아닙니다. .jpg, .jpeg, .png 파일을 선택해주세요.');
		$("#bookCoverImg").val('');
		return false;
	}

	return true;
}