/**
 * 人员管理js
 */
function updatePrivilege(obj){
	var index=$(obj).attr("id");
	var updatePriModal=$('#mfm_upadte_modal');
	var mfm_table=document.getElementById('mfm_table');
		
	var id=mfm_table.rows[index].cells[0].innerText;
	var mainPVL=mfm_table.rows[index].getElementsByTagName("input")[0].value;
	var uploadPVL=mfm_table.rows[index].getElementsByTagName("input")[1].value;
	var updatePVL=mfm_table.rows[index].getElementsByTagName("input")[2].value;
	var grantPVL=mfm_table.rows[index].getElementsByTagName("input")[3].value;
	var allFilePVL=mfm_table.rows[index].getElementsByTagName("input")[4].value;
	
	if(mainPVL==1){
		$('#checkbox_mainPVL').attr('checked','checked');
	}
	if(uploadPVL==1){
		$('#checkbox_uploadPVL').attr('checked','checked');
	}
	if(updatePVL==1){
		$('#checkbox_updatePVL').attr('checked','checked');
	}
	if(grantPVL==1){
		$('#checkbox_grantPVL').attr('checked','checked');
	}
	if(allFilePVL==1){
		$('#checkbox_allFilePVL').attr('checked','checked');
	}
	
	
	updatePriModal.modal('show');
}