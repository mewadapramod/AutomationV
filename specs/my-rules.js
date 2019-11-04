rule("distant %{pixels} px from last %{menuItemsObj}", function (objectName, parameters) {
    var allObjects = findAll(parameters.menuItemsObj);
    if (allObjects.length > 0) {
        var count = allObjects.length;
        for(var i = allObjects.length - 1; i >=0; i--){
            if(allObjects[i].isVisible()){
               break; 
            } else{
                count--;
            }
        }
        if(count > 0) {
            menuItem = find("menu-items-" + count);
            var menuItemPageElement = menuItem.getPageElement();
            var expectedValue = parseFloat(parameters.pixels) - parseFloat(menuItemPageElement.getCssProperty("padding-right"));
            this.addSpecs(["right-of " + menuItem.name + " ~" + expectedValue  + " px"]);
        }
    } else {
        throw new Error("No menu items found");
    }
});

rule("height %{pixels} px", function (objectName, parameters) {
    var paddingTop = parseFloat(getCssProperty(objectName, "padding-top"));
    var paddingBottom = parseFloat(getCssProperty(objectName, "padding-bottom"));

    var data = parameters.pixels;
    if(data.startsWith("~")){
        data = data.trim().split("~")[1].trim();
        var expectedValue = parseFloat(data) + paddingTop + paddingBottom;
        this.addSpecs(["height ~" + expectedValue  + " px"]);
    }
});

rule("width %{pixels} px", function (objectName, parameters) {
    var paddingLeft = parseFloat(getCssProperty(objectName, "padding-left"));
    var paddingRight = parseFloat(getCssProperty(objectName, "padding-right"));

    var data = parameters.pixels;
    if(data.startsWith("~")){
        data = data.trim().split("~")[1].trim();
        var expectedValue = parseFloat(data) + paddingLeft + paddingRight;
        this.addSpecs(["width ~" + expectedValue  + " px"]);
    }
});

rule("left-of %{nextItem} %{pixels} px", function (objectName, parameters) {
    var nextItemObj = find(parameters.nextItem);

    var nextItemObjPaddingLeft = parseFloat(getCssProperty(parameters.nextItem, "padding-left"));
    var objPaddingRight = parseFloat(getCssProperty(objectName, "padding-right"));

    var data = parameters.pixels;
    if(data.startsWith("~")){
        data = data.trim().split("~")[1].trim();
        var expectedValue = parseFloat(data) - nextItemObjPaddingLeft - objPaddingRight;
        this.addSpecs(["left-of " + nextItemObj.name + " ~" + expectedValue  + " px"]);
    }
});

rule("right-of %{prevItem} %{pixels} px", function (objectName, parameters) {
    var prevItemObj = find(parameters.prevItem);

    var prevItemObjPaddingRight = parseFloat(getCssProperty(parameters.prevItem, "padding-right"));
    var objPaddingLeft = parseFloat(getCssProperty(objectName, "padding-left"));

    var data = parameters.pixels;
    if(data.startsWith("~")){
        data = data.trim().split("~")[1].trim();
        var expectedValue = parseFloat(data) - prevItemObjPaddingRight - objPaddingLeft;
        this.addSpecs(["right-of " + prevItemObj.name + " ~" + expectedValue  + " px"]);
    }
});

rule("above %{nextItem} %{pixels} px", function (objectName, parameters) {
    var nextItemObj = find(parameters.nextItem);

    var nextItemObjPaddingTop = parseFloat(getCssProperty(parameters.nextItem, "padding-top"));
    var objPaddingBottom = parseFloat(getCssProperty(objectName, "padding-bottom"));

    var data = parameters.pixels;
    if(data.startsWith("~")){
        data = data.trim().split("~")[1].trim();
        var expectedValue = parseFloat(data) - nextItemObjPaddingTop - objPaddingBottom;
        this.addSpecs(["above " + nextItemObj.name + " ~" + expectedValue  + " px"]);
    }
});

rule("inside %{parentItem} %{pixels} px left", function (objectName, parameters) {    
    var parentItemObj = find(parameters.parentItem);

    var parentItemPaddingRight = parseFloat(getCssProperty(parameters.parentItem, "padding-right"));
    var objPaddingLeft = parseFloat(getCssProperty(objectName, "padding-left"));
    var data = parameters.pixels;
    if(data.startsWith("~")){
        data = data.trim().split("~")[1].trim();
        var expectedValue = parseFloat(data) - parentItemPaddingRight - objPaddingLeft;
        this.addSpecs(["inside " + parentItemObj.name + " ~" + expectedValue  + " px left"]);
    }
});

rule("if %{objectName} is visible", function (scopeObject, parameters) {
    var obj = find(parameters.objectName);
    if (obj.isVisible()) {
        this.doRuleBody();
    }
});

rule("if visible", function (objectName, parameters) {
    var obj = find(objectName);
    if (obj.isVisible()) {
        this.doRuleBody();
    }
});

rule("verify %{totalTicketAmount} is sum of %{currency} %{costs} having %{totalParkingAmount} parking %{desc}", function (objectName, parameters) {
    var obj = find(parameters.totalParkingAmount);
    var allObjects = findAll(parameters.costs);
    var eventNames = findAll(parameters.desc);
    var sum = 0;
    for(var i = 0 ; i < allObjects.length; i+=2) {
        if(allObjects[i].getPageElement().getText().trim().startsWith(parameters.currency)){
            var eventName = eventNames[i].getPageElement().getText().trim().toLowerCase();
            if(eventName.indexOf("parking") > -1 && obj.isVisible()) {
                continue;
            }
            sum += parseFloat(allObjects[i].getPageElement().getText().trim().substring(parameters.currency.length()).trim().replace("," , ""));
        }
    }
    
    this.addObjectSpecs(parameters.totalTicketAmount, ["text is \"" + parameters.currency + formatMoney(sum, 2) + "\""]);
});

rule("verify %{totalParkingAmount} is sum of %{currency} %{costs} having parking %{desc}", function (objectName, parameters) {
    var obj = find(parameters.totalParkingAmount);
    if (obj.isVisible()) {
        var allObjects = findAll(parameters.costs);
        var eventNames = findAll(parameters.desc);
        var sum = 0;
        for(var i = 0 ; i < allObjects.length; i+=2) {
            if(allObjects[i].getPageElement().getText().trim().startsWith(parameters.currency)){
                var eventName = eventNames[i].getPageElement().getText().trim().toLowerCase();
                if(eventName.indexOf("parking") > -1) {
                    sum += parseFloat(allObjects[i].getPageElement().getText().trim().substring(parameters.currency.length()).trim().replace("," , ""));
                }
            }
        }
        
        this.addObjectSpecs(parameters.totalParkingAmount, ["text is \"" + parameters.currency + formatMoney(sum, 2) + "\""]);
    }
});

rule("verify amount due is sum of %{currency} %{otheramounts}", function (objectName, parameters) {
    var allObjects = findAll(parameters.otheramounts);
    var sum = 0;
    var amountDue = allObjects[allObjects.length - 1].name;
    for(var i = 0 ; i < allObjects.length - 2; i++) {
        if(allObjects[i].getPageElement().getText().trim().startsWith(parameters.currency)) {
            sum += parseFloat(allObjects[i].getPageElement().getText().trim().substring(parameters.currency.length()).trim().replace("," , ""));
        }
    }

    var less = 0;
    if(allObjects[allObjects.length - 2].getPageElement().getText().trim().startsWith(parameters.currency)) {
        less = parseFloat(allObjects[allObjects.length - 2].getPageElement().getText().trim().substring(parameters.currency.length()).trim().replace("," , ""));
    }
    
    var actualAmount = sum - less;

    this.addObjectSpecs(amountDue, ["text is \"" + parameters.currency + formatMoney(actualAmount, 2) + "\""]);
});

this.formatMoney = function(value, c, d, t){
var n = value, 
    c = isNaN(c = Math.abs(c)) ? 2 : c, 
    d = d == undefined ? "." : d, 
    t = t == undefined ? "," : t, 
    s = n < 0 ? "-" : "", 
    i = String(parseInt(n = Math.abs(Number(n) || 0).toFixed(c))), 
    j = (j = i.length) > 3 ? j % 3 : 0;
   return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
};