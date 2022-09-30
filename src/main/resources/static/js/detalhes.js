window.onload = function(){
	const descricao = document.getElementById("descricaoLonga");
	console.log(descricao);
	let parser = new DOMParser();
	let documentoHTML = parser.parseFromString(descricao.value, "text/html");
	console.log(documentoHTML);
	var result = document.getElementById("descricaoLonga").innerHTML;
	console.log(result);
	document.getElementById("maisInfo").innerHTML = descricao.textContent;
	//document.getElementById("descricaoLonga").innerHTML = descricao.textContent;
	//document.getElementById("descricaoLonga").appendChild(result);
}