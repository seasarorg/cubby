/**
 * Figure
 * HTML要素に対してイベント処理を追加します。
 * @class Figure
 * @param {htmlElementt} 必須。HTML要素を渡します。
 * @param {parameters} 任意。デフォルト値にしたいパラメータをハッシュで渡します。
 * @constructor
 */
function Figure(htmlElement, parameters) {
		// 事前条件
		if (!htmlElement || typeof(htmlElement)!="object") throw "エラー:htmlElementにはHTML要素を指定してください。";
		if (parameters && typeof(parameters)!="object") throw "エラー:parametersにはハッシュを指定してください。";
		for (var parameter in parameters) {
				if (typeof(this[parameter])=="undifined") throw "エラー:parameters[" + parameter + "]はFigureに存在しないパラメータです。";
		}
		
		// 代入
		this.htmlElement = htmlElement;
		for (parameter in parameters) { this[parameter] = parameters[parameter]; }
		var self = this;

		// イベントリスナーのセット
		Cubby.whiteboard.Event.addListener(htmlElement, "mousedown", function(event) { self.handleEvent(event); });
		Cubby.whiteboard.Event.addListener(htmlElement, "mouseup", function(event) { self.handleEvent(event); });
		Cubby.whiteboard.Event.addListener(htmlElement, "mouseover", function(event) { self.handleEvent(event); });
		Cubby.whiteboard.Event.addListener(htmlElement, "mousemove", function(event) { self.handleEvent(event); });
		Cubby.whiteboard.Event.addListener(htmlElement, "mouseout", function(event) { self.handleEvent(event); });

		// ステートマシーンの初期状態をセットします。
		this.currentState = this.initialState;
}

Figure.prototype = {
		initialState: "Inactive",
		currentState: null,  // 現在の状態
		sx: null,  // startCursorX
		sy: null,  // startCursorY
		console: null,  // debug用のコンソール

		/**
		 * マウスとタイマーのイベントハンドラーメソッド。
		 * @param {event}	イベント
		 */
		handleEvent : function(event){
				var actionTransitionFunction = this.actionTransitionFunctions[this.currentState][event.type];
				if (!actionTransitionFunction) { actionTransitionFunction = this. unexceptionEvent; }
				var nextState = actionTransitionFunction.call(this, event);
				if (!nextState) { nextState = this.currentState; }
				if (!this.actionTransitionFunctions[nextState]) { nextState = this.undefinedState(event, nextState); }
				if (this.currentState != nextState) {
						this.console.innerHTML = "State:" + this.currentState + "->" + nextState;
				}
				this.currentState = nextState;
		},

		unexceptionEvent: function(event) {
				//this.cancelTimer();
				//this.cancelTicker();
				//this.deleteTooltip();
				alert("Tooltip handled unexcepted event : " + event.type + " in state " + this.currentState + " for id=" + this.htmlElement.id + " running browser " + window.navigator.userAgent);
				return this.initialState;
		},

		undefinedState: function(event, state) {
				//this.cancelTimer();
				//this.cancelTicker();
				//this.deleteTooltip();
				alert("Tooltip transitioned to undefined state : " + state + " from state " + this.currentState + " due to event " + event.type + " for id=" + this.htmlElement.id + " running browser " + window.navigator.userAgent);
				return this.initialState;
		},

		// 状態マシーンを表す２次元テーブル
		// actionTransionFunctions[状態][イベント処理関数]
		// イベント処理関数は戻り値として、次の状態を返します。

		actionTransitionFunctions: {
				// 初期状態
				Inactive: {
						mouseover: function(event) {
								return this.currentState; // 何もしない
						}, 
						mousemove: function(event) {
								return this.currentState; // 何もしない
								//return this.doActionTransition("Inactive", "mouseover", event);
						}, 
						mouseout: function(event) {
								return this.currentState; // 何もしない
						},
						mouseup: function(event) {
								return this.currentState; // 何もしない
						},
						mousedown: function(event) {
								return "Pause";
						}
				}, // end of Inactive

				// 待機状態
				Pause: {
						mouseover: function(event) {
								return this.currentState;
						}, 
						mousemove: function(event) {
								this.saveCursorStartPosition(event.pageX, event.pageY);
								return "Drawing";
						}, 
						mouseout: function(event) {
								return "Inactive";
						},
						mouseup: function(event) {
								return "Inactive";
						},
						mousedown: function(event) {
								throw "Invalid"; // ありえない
						}
				}, // end of Pause

				// 描画状態
				Drawing: {
						mouseover: function(event) {
								return this.currentState; // 何もしない
						}, 
						mousemove: function(event) {
								return this.currentState; // 何もしない
						}, 
						mouseout: function(event) {
								return this.currentState; // 何もしない
						},
						mouseup: function(event) {
								this.createLine(event.pageX, event.pageY);
								return "Inactive";
						},
						mousedown: function(event) {
								return this.currentState; // 何もしない
						}
				} // end of Drawing
		},
		createLine: function(ex, ey) {
				var x = ex - this.sx;
				var y = ey - this.sy;
				var topY = Math.min(this.sy, ey);
				var leftX = Math.min(this.sx, ex);
				var div = document.createElement('div');
				this.htmlElement.appendChild(div);
				with (div.style) {
						backgroundColor = 'gray';
						width = Math.abs(x) + "px";
						height = Math.abs(y) + "px";
						left = leftX + 'px';
						top = topY + 'px';
						position = 'absolute';
						zIndex = '3';
				}
				var img = document.createElement('img');
				img.className = 'line';
				div.appendChild(img);
				img.width = Math.abs(x);
				img.height = Math.abs(y);
				img.src = "/cubby-examples/whiteboard/api/line/" + x + "/" + y + "/";
		},
		saveCursorStartPosition: function(x, y) {
				this.sx = x;
				this.sy = y;
		}
};

