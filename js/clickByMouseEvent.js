/**
 * @Author : Syed Tarif Abbas Rizvi
 */
var controlKeyDown = false;
var altKeyDown = false;
var shiftKeyDown = false;
var metaKeyDown = false;

function triggerMouseEvent(element, eventType, canBubble, clientX, clientY,
		button) {

	clientX = clientX ? clientX : 0;
	clientY = clientY ? clientY : 0;

	var screenX = 0;
	var screenY = 0;

	canBubble = (typeof (canBubble) == undefined) ? true : canBubble;
	var evt;
	if (element.fireEvent && element.ownerDocument
			&& element.ownerDocument.createEventObject) { // IE
		evt = createEventObject(element, controlKeyDown, altKeyDown,
				shiftKeyDown, metaKeyDown);
		evt.detail = 0;
		evt.button = button ? button : 1; // default will be the left mouse
		// click (
		// http://www.javascriptkit.com/jsref/event.shtml
		// )
		evt.relatedTarget = null;
		if (!screenX && !screenY && !clientX && !clientY && !controlKeyDown
				&& !altKeyDown && !shiftKeyDown && !metaKeyDown) {
			element.fireEvent('on' + eventType);
		} else {
			evt.screenX = screenX;
			evt.screenY = screenY;
			evt.clientX = clientX;
			evt.clientY = clientY;

			// when we go this route, window.event is never set to contain the
			// event we have just created.
			// ideally we could just slide it in as follows in the try-block
			// below, but this normally
			// doesn't work. This is why I try to avoid this code path, which is
			// only required if we need to
			// set attributes on the event (e.g., clientX).
			try {
				window.event = evt;
			} catch (e) {
				// getting an 'Object does not support this action or property'
				// error. Save the event away
				// for future reference.
				// TODO: is there a way to update window.event?

				// work around for http://jira.openqa.org/browse/SEL-280 -- make
				// the event available somewhere:
				// selenium.browserbot.getCurrentWindow().selenium_event = evt;
			}
			element.fireEvent('on' + eventType, evt);
		}
	} else {
		var doc = element.ownerDocument;
		var view = doc.parentWindow;

		evt = doc.createEvent('MouseEvents');
		if (evt.initMouseEvent) {
			// see http://developer.mozilla.org/en/docs/DOM:event.button and
			// http://developer.mozilla.org/en/docs/DOM:event.initMouseEvent for
			// button ternary logic logic
			// Safari
			evt.initMouseEvent(eventType, canBubble, true, view, 1, screenX,
					screenY, clientX, clientY, controlKeyDown, altKeyDown,
					shiftKeyDown, metaKeyDown, button ? button : 0, null);
			if (element.nodeName == 'INPUT') {
				debugger
			}
		} else {
			evt.initEvent(eventType, canBubble, true);
			evt.shiftKey = shiftKeyDown;
			evt.metaKey = metaKeyDown;
			evt.altKey = altKeyDown;
			evt.ctrlKey = controlKeyDown;
			if (button) {
				evt.button = button;
			}
		}
		element.dispatchEvent(evt);
	}
}

function performClick(element, clientX, clientY) {
	var savedEvent = null;
	element.addEventListener('click', function(evt) {
		savedEvent = evt;
	}, false);
	triggerMouseEvent(element, 'click', true, clientX, clientY);
	triggerMouseEvent(element, 'mousedown', true, clientX, clientY);
	triggerMouseEvent(element, 'mouseup', true, clientX, clientY);
}
