<script>
function actionPerformed(userAction) {
	window.document.getElementById("userAction").value = userAction;
	window.document.forms[0].submit();
}
</script>