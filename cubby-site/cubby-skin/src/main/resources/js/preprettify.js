prePrettyPrint = function() {
	var elements = document.getElementsByTagName("pre");
	var element;
	var parent;
	for (i in elements) {
		element = elements[i];
		parent = element.parentNode;
		if (parent && parent.getAttribute("class") == "source") {
			element.setAttribute("class", "prettyprint");
		}
	}
	prettyPrint();
}
