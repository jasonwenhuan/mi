
/*var object = {};
var newclass1 = {};
function Class(){
	this.create = function(baseclass,define){
		function klass(){
			this.initialize.apply(this, arguments);
			for(var member in define){
	        	this[member] = define[member];
	        }
		};
        klass.prototype = baseclass;
		return klass;
	}
};
var Classes = new Class();
var table = Classes.create(object,{
	initialize : function($super){
		alert("this is test");
	}
});*/

/*var animal = function(){};
animal.prototype = {
		start : function(){
			alert("start");
		},
		stop : function(){
			alert(stop);
		}
}*/



/*Function.prototype.method = function(name,fn){
	this.prototype[name] = fn;
	return this;
}
var animail = function(){};
animail.method('start', function(){
	alert("start");
});
animail.method('stop', function(){
	alert("stop");
});
var bza;
(function(){
	var a = 10;
	var b = 5;
	bza = function(){
		alert("wenhuan");
		return a*b;
	}
})();*/

/*function person(name){
	this.name = name;
}

function author(name,books){
	person.call(this,name);
	this.books = books;
}

author.prototype = new person();
author.prototype.constructor = author;
author.prototype.getBook = function(){
	alert(this.name + this.books + "hahhah");
};*/




/*function foo(){
	alert(this.fruit);
};
var fruit = 'apple';
var pack = {
		fruit : 'oringe',
}*/

/*function Person(name) {
	this.name = name;
}
Person.prototype = {
	getName : function() {
		return this.name;
	}
}

function Employee(name,eId){
	this.name = name;
	this.eId = eId;
}

Employee.prototype = new Person();
Employee.prototype.getEid = function(){
	return this.eId;
}
Employee.prototype.constructor = Employee;*/
/*function Person(){};
function Employee(){};
Person.prototype = {
    init : function(name){
    	this.name = name;
    },
    getName : function(){
    	return this.name;
    }
}
Employee.prototype = new Person();
Employee.prototype.constructor = Employee;
Employee.prototype.init = function(name,eId){
	this.name = name;
	this.eId = eId;
};
Employee.prototype.getEid = function(){
	return this.eId;
};*/

/*
var initializing = false;
function Person(){
	if(!initializing){
		this.init.apply(this,arguments);
	}
}
Person.prototype = {
	init : function(name){
		this.name = name;
	},
	getName : function(){
		return this.name;
	}
}
function Employee(){
	if(!initializing){
		this.init.apply(this,arguments);
	}
}
initializing = true;
Employee.prototype = new Person();
Employee.prototype.constructor = Employee;
initializing = false;
Employee.prototype.init = function(name,eId){
	this.name = name;
	this.eId = eId;
}
Employee.prototype.getEid = function(){
	return this.eId;
}*/




/*var initializing = true;
function jClass(baseClass,prop){
	if(typeof(baseClass) === "object"){
		prop = baseClass;
		baseClass = null;
	}
	function f(){
		if(!initializing){
			if(baseClass){
				this.base = baseClass.prototype;
			}
			this.init.apply(this,arguments);
		}
	}
	if(baseClass){
		initializing = true;
		f.prototype = new baseClass();
		f.prototype.constructor = f;
		initializing = false;
	}
	for(var name in prop){
		if(prop.hasOwnProperty(name)){
			f.prototype[name] = prop[name];
		}
	}
	return f;
}

var Person = jClass({
	init : function(name){
		this.name = name;
	},
	getName : function(){
		return this.name;
	}
});

var Employee = jClass(Person,{
	init : function(name,eId){
		//this.name = name;
		this.base.init.apply(this,[name]);
		this.eId = eId;
	},
	getEid : function(){
		return this.eId;
	}
});*/

/*
var initializing = false;
function jClass(baseClass,prop){
	if(typeof(baseClass)=== "object"){
		prop = baseClass;
		baseClass = null;
	}
	function f(){
		if(!initializing){
			if(baseClass){
				this.baseprototype = baseClass.ptototype;
			}
			this.init.apply(this,arguments);
		}
	}
	if(baseClass){
		initializing = true;
		f.prototype = new baseClass();
		f.prototype.constructor = f;
		initializing = false;
	}
	for(var name in prop){
		if(prop.hasOwnProperty(name)){
			if(baseClass && typeof(prop[name]) === "function" && typeof(f.prototype[name] === "function")){
				f.prototype[name] = (function(name,fn){
					return function(){
						this.base = baseClass.prototype[name];
						return fn.apply(this,arguments);
					};
				})(name,prop[name]);
			}else{
				f.prototype[name] = prop[name];
			}
		}
	}
	return f;
}

var Person = jClass({
	init : function(name){
		this.name = name;
	},
	getName : function(){
		return this.name;
	}
});

var Employee = jClass(Person,{
	init : function(name,eId){
		//this.name = name;
		this.base(name);
		this.eId = eId;
	},
	getEid : function(){
		return this.eId;
	}
});*/



/*Function.prototype.inherits = function(parent){
	this.prototype = new parent();
	this.prototype.constructor = this;
	this.prototype.uber = function(name){
		f = parent.prototype[name];
		return f.apply(this,Array.prototype.slice.call(arguments, 1));
	};
}
function person(name){
	this.name = name;
}
person.prototype.getName = function(prefix){
	return prefix + this.name;
}
function employee(name,eId){
	this.name = name;
	this.eId = eId;
}
employee.inherits(person);
employee.prototype.getName = function(){
	return this.uber("getName","Employee name :");
}*/

/*
 * author : Jason Wen
 * 
 * reference JavaScript framework
 */ 
//构建类，实现继承
(function(){
	//当前是否处于创建类的阶段
	var initializing = false;
	jClass = function(){};
	//定义继承
	jClass.extend = function(prop){
		var baseClass = null;
		if(this != jClass){
			baseClass = this;
		}
		function f(){
			if(!initializing){
				if(baseClass){
					this._superprototype = baseClass.prototype;
				}
				this.init.apply(this,arguments);
			}
		}
		if(baseClass){
			initializing = true;
			f.prototype = new baseClass();
			f.prototype.constructor = f;
			initializing = false;
		}
		//新创建的类自动附加extend函数
		f.extend = arguments.callee;
		//覆盖父类同名函数
		for(var name in prop){
			if(prop.hasOwnProperty(name)){
				if(baseClass && typeof(prop[name]) === "function" && typeof(f.prototype[name]) === "function" && /\b_super\b/.test(prop[name])){
					f.prototype[name] = (function(name,fn){
						return function(){
							this._super = baseClass.prototype[name];
							return fn.apply(this,arguments);
						}
					})(name,prop[name]);
				}else{
					f.prototype[name] = prop[name];
				}
			}
		}
		return f;
	};
})();

//下面两个类仅作为test
var Person = jClass.extend({
	init : function(name){
		this.name = name;
	},
	getName : function(prefix){
		return prefix + this.name;
	}
});

var Employee = Person.extend({
	init : function(name,eId){
		this._super(name);
		this.eId = eId;
	},
	getEid : function(){
		//通过这种方式调用父类中的其他函数
		var name = this._superprototype.getName.call(this,"Employee id and name :");
		return name + this.eId;
	},
	getName : function(){
		return this._super("Employee name :");
	}
});

function test(){
	alert("hahha");
	var zhang = new Employee("zhangsan","1234");
	alert(zhang.getName());
	alert(zhang.getEid());
}
