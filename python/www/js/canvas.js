/*
(function(window) {
var XXX = (function() {
}).extend(Object);

com.igift.XXX = XXX;
})(window);
*/

(function(window) {
	com.igift.Constrants = {
		mode: "2d",
		G: 0.618,
	};
})(window);

(function(window) {
	var IndexData = (function() {
		this._id = IndexData.prototype.sequence++;
		if (arguments.length == 1) {
			this._id = arguments[0];
		}
	}).extend(Object);

	IndexData.prototype.sequence = 0;

	IndexData.prototype.id = function() {
		if (arguments.length == 1) {
			this._id = arguments[0];
		}
		return this._id || -1;
	};

	com.igift.IndexData = IndexData;
})(window);

(function(window) {
	var Shape = (function() {
		Shape.prototype.super.apply(this, arguments);

		if (arguments.length == 1) {
			this.id(arguments[0]);
		} else if (arguments.length == 4) {
			this.x(arguments[0]);
			this.y(arguments[1]);
			this.width(arguments[2]);
			this.height(arguments[3]);
		} else if (arguments.length == 5) {
			this.id(arguments[0]);
			this.x(arguments[1]);
			this.y(arguments[2]);
			this.width(arguments[3]);
			this.height(arguments[4]);
		} else {

		}
	}).extend(com.igift.IndexData);

	Shape.prototype.x = function() {
		if (arguments.length == 1) {
			this._x = arguments[0];
		}
		return this._x || 0;
	};
	Shape.prototype.y = function() {
		if (arguments.length == 1) {
			this._y = arguments[0];
		}
		return this._y || 0;
	};
	Shape.prototype.width = function() {
		if (arguments.length == 1) {
			this._width = arguments[0];
		}
		return this._width || 0;
	};
	Shape.prototype.height = function() {
		if (arguments.length == 1) {
			this._height = arguments[0];
		}
		return this._height || 0;
	};
	Shape.prototype.draw = function(ctx) {};
	Shape.prototype.clear = function(ctx) {
		ctx.clearRect(this.x(), this.y(), this.width(), this.height());
	};
	Shape.prototype.reDraw = function(ctx) {
		this.clear();
		this.draw();
	};
	Shape.prototype.animate = function(ctx) {};

	com.igift.Shape = Shape;
})(window);

(function(window) {
	var Heart = (function() {
		Heart.prototype.super.apply(this, arguments);
	}).extend(com.igift.Shape);
	Heart.prototype.draw = function(ctx) {
		var g = com.igift.Constrants.G;
		var ratio = 1 - g;
		var centerX = this.x() + this.width() / 2;
		var top = {x: centerX, y: this.y() + this.height() * ratio};
		var leftTop = {x: this.x(), y: this.y() + this.height() * ratio * ratio};
		var leftBottom = {x: this.x(), y: this.y() + this.height() * g};
		var bottom = {x: centerX, y: this.y() + this.height()};
		var fillPoi = {x: this.x() + this.width() * g, y: this.y() + this.height() * g};
		var gradient = ctx.createRadialGradient(fillPoi.x, fillPoi.y, 0, fillPoi.x, fillPoi.y, this.width() * g);
		gradient.addColorStop(0, '#fff');
		gradient.addColorStop(1, '#f00');
		ctx.save();
		ctx.fillStyle = gradient;
		ctx.strokeRect(this.x(), this.y(), this.width(), this.height());
		ctx.beginPath();
		ctx.moveTo(top.x, top.y);
		ctx.bezierCurveTo(leftTop.x, leftTop.y, leftBottom.x, leftBottom.y, bottom.x, bottom.y);
		ctx.moveTo(top.x, top.y);
		ctx.bezierCurveTo(leftTop.x + this.width(), leftTop.y, leftBottom.x + this.width(), leftBottom.y, bottom.x, bottom.y);
		ctx.fill();
		ctx.restore();
	};
	com.igift.Heart = Heart;
})(window);


(function(window) {
	var Layer = (function(canvas) {
		//this.super();
		this.container = canvas;
		this.ctx = canvas.getContext(com.igift.Constrants.mode);
	}).extend(com.igift.IndexData);

	Layer.prototype.width = function() {
		if (arguments.length == 1) {
			this.container.width = arguments[0];
		}
		return this.container.width;
	};
	Layer.prototype.height = function() {
		if (arguments.length == 1) {
			this.container.height = arguments[0];
		}
		return this.container.height;
	};
	Layer.prototype.draw = function() {
		var shape = new com.igift.Heart(50, 20, 400, 400);
		shape.draw(this.ctx);
	};

	com.igift.Layer = Layer;
})(window);

(function(window) {
	var DrawingBoard = (function(dom) {
		this.container = dom;
		_init.call(this);
	}).extend(Object);
	var _init = function() {
			var $dom = $(this.container);
			this.width = $dom.width();
			this.height = $dom.height();
			var canvas = $("<canvas />");
			$dom.append(canvas);
			var layer = new com.igift.Layer(canvas.get(0));
			layer.width(this.width);
			layer.height(this.height);
			layer.draw();
		};

	com.igift.DrawingBoard = DrawingBoard;
})(window);