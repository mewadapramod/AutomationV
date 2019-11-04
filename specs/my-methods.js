this.isNumber = function (n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
};

this.getCssProperty = function (objectName, propertyName) {
    var obj = find(objectName);
    var pageElement = obj.getPageElement();
    return pageElement.getCssProperty(propertyName);
};

this.getText = function (objectName) {
    var obj = find(objectName);
    var pageElement = obj.getPageElement();
    return pageElement.getText();
};

this.getScrollbarWidth = function (parentObj, childObj) {
	var parentobjWidth = find(parentObj).getPageElement().getWidth();
	var childobjWidth = find(childObj).getPageElement().getWidth();

	return parentobjWidth - childobjWidth;
};

this.getUserName = function (objectName) {
	var obj = find(objectName);
	var pageElement = obj.getPageElement();
	var sText = pageElement.getText();
	sText = sText.split(' keyboard_arrow_up')[0].split(' arrow_drop_up')[0];
	sText = sText.trim().replace("arrow_drop_up" , "").trim();
	if(sText.contains("@")) {
		sText = sText.split('@')[0];
	}

	return sText;
}