/**
 * 人员管理js
 */
function updatePrivilegeModal(obj) {
	var fatherBody = $(window.top.document.body);
	var index = $(obj).attr("id");
//	var updatePriModal = window.parent.document.getElementById('mfm_upadte_modal');
	var updatePriModal =$('#mfm_upadte_modal');
	var mfm_table = document.getElementById('mfm_table');

	var id = mfm_table.rows[index].cells[0].innerText;
	var mainPVL = mfm_table.rows[index].getElementsByTagName("input")[0].value;
	var uploadPVL = mfm_table.rows[index].getElementsByTagName("input")[1].value;
	var updatePVL = mfm_table.rows[index].getElementsByTagName("input")[2].value;
	var grantPVL = mfm_table.rows[index].getElementsByTagName("input")[3].value;
	var allFilePVL = mfm_table.rows[index].getElementsByTagName("input")[4].value;

	// 设置modal初始值
	$('#mfm_id').attr('value', id);

	if (mainPVL == 1) {
		$('#checkbox_mainPVL').attr('checked', 'checked');
	} else {
		$('#checkbox_mainPVL').removeAttr('checked');
	}

	if (uploadPVL == 1) {
		$('#checkbox_uploadPVL').attr('checked', 'checked');
	} else {
		$('#checkbox_uploadPVL').removeAttr('checked');
	}

	if (updatePVL == 1) {
		$('#checkbox_updatePVL').attr('checked', 'checked');
	} else {
		$('#checkbox_updatePVL').removeAttr('checked');
	}

	if (grantPVL == 1) {
		$('#checkbox_grantPVL').attr('checked', 'checked');
	} else {
		$('#checkbox_grantPVL').removeAttr('checked');
	}

	if (allFilePVL == 1) {
		$('#checkbox_allFilePVL').attr('checked', 'checked');
	} else {
		$('#checkbox_allFilePVL').removeAttr('checked');
	}

	//fatherBody.append("<div id='backdropId' class='modal-backdrop fade in'></div>");
/*	$('#mfm_upadte_modal').on('shown.bs.modal',function(){
		$(".modal-backdrop").remove();
	});*/
	updatePriModal.modal({
		  backdrop:false
		})
	updatePriModal.modal('show');

}



function updateMFMPrivilege() {
	var contextPath = window.document.location.pathname;
	var id = 0;
	var mainPVL = 0;
	var uploadPVL = 0;
	var updatePVL = 0;
	var grantPVL = 0;
	var allFilePVL = 0;

	id = $('#mfm_id').attr('value');
	if ($('#checkbox_mainPVL').attr('checked') == 'checked') {
		mainPVL = 1;
		$('#checkbox_mainPVL').attr('value', '1');
	} else {
		$('#checkbox_mainPVL').attr('value', '0');
	}

	if ($('#checkbox_uploadPVL').attr('checked') == 'checked') {
		uploadPVL = 1;
		$('#checkbox_uploadPVL').attr('value', '1');
	} else {
		$('#checkbox_mainPVL').attr('value', '0');
	}

	if ($('#checkbox_updatePVL').attr('checked') == 'checked') {
		updatePVL = 1;
		$('#checkbox_updatePVL').attr('value', '1');
	} else {
		$('#checkbox_mainPVL').attr('value', '0');
	}

	if ($('#checkbox_grantPVL').attr('checked') == 'checked') {
		grantPVL = 1;
		$('#checkbox_grantPVL').attr('value', '1');
	} else {
		$('#checkbox_mainPVL').attr('value', '0');
	}

	if ($('#checkbox_allFilePVL').attr('checked') == 'checked') {
		allFilePVL = 1;
		$('#checkbox_allFilePVL').attr('value', '1');
	} else {
		$('#checkbox_mainPVL').attr('value', '0');
	}

	// $.ajax({
	// url:'/myfiles/MFM/updatePrivilege.do',
	// type:'POST',
	// asyn:false,
	// data:$('#mfm_update_form').serialize(),
	// success:(function(){
	// window.location.reload();
	// })
	// });

}