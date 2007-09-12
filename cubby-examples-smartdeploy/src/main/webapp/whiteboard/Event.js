Cubby.namespace("whiteboard");

// 1回目のみ作成
if (!Cubby.whiteboard.Event) {
		/**
		 * イベントのアタッチ・デタッチを行うクラス	 
		 * @namespace Cubby.whiteboard
		 * @class Event
		 */
		Cubby.whiteboard.Event = function() {
        return { // PREPROCESS
						/**
						 * イベントリスナーを追加します。
						 * @param {HTMLElement} el HTML要素。例：mouseover
						 * @param {String} type イベントの種別。例：mouseover
						 * @param {Function} fn イベントリスナー 
						 */
						addListener: function(el, type, fn) {
								var wrappedFn = function(event) {
										return fn.call(el, Cubby.whiteboard.Event.getEvent(event), el);
								};
								if (el.addEventListener) { // Firefox/Netscape/Operaの場合
										el.addEventListener(type, wrappedFn, false);
								}
								else if (el.attachEvent) { // MSIEの場合
										el.attachEvent(type, wrappedFn);
								}
								else { // それ以外の古いブラウザの場合
										var previousListener = el["on" + type];
										el["on" + type] = function(event) {
												wrappendFn.call(el, event, el);
												if (previousListener) {
														el.previousHandler = previousListener;
														el.previousHandler(Cubby.whiteboard.Event.getEvent(event));
												}
										};
								}
						},
						getEvent: function(event) {
								return event ? event : window.event;
						} // end of Event.getEvent
				}
		}(); // end of Event
}

