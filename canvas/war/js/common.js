(function(window) {
	if (!window.com) {
		window.com = {};
	}
	if (!window.com.igift) {
		com.igift = {};
	}

	Function.prototype.extend = function(superClass) {
		var f = function() {};
		f.prototype = superClass.prototype;
		this.prototype = new f();
		this.prototype.constructor = this;
		this.prototype.super = superClass;
		return this;
	}
})(window);

(function(window) {
	var Dictionary = (function() {
		this.datas = [];
		this.index = {};
	}).extend(Object);
	Dictionary.prototype.put = function(data) {
		if (!this.index[data.id()]) {
			datas.push(data);
			this.index[data.id()] = data;
		}
	};
	Dictionary.prototype.get = function(id) {
		return this.index[id];
	};
	Dictionary.prototype.remove = function(id) {
		if (this.index[id]) {
			delete this.index[id];
			for (var i = 0; i < this.datas.length; i++) {
				if (this.datas[i].id() === id) {
					this.datas.splice(i, 1);
				}
			}
		}
	};

	com.igift.Dictionary = Dictionary;
})(window);