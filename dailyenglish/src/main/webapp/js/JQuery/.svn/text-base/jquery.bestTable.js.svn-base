//jquery自定义插件格式
/*(function($){
 $.fn.bestTable = function(options){
 var defaults = {};
 var opts = $.extend(defaults,options);
 this.each(function(){

 });
 }
 })(jQuery);*/
//wenhuan 待修改完善
(function($) {
	$.fn.bestTable = function(options) {
		var defaults = {
		    sortable : true,
			isDrog : false,
			oddtrBackgroundColor : "#DDDDDD",
			isEffect : true,
			effectBackgroundColor : "#FFFFCC",
			isEditor : false,
			isEditorNewColor : true,
			editorNewColorBackgroundColor : "green"
		};
		var opts = $.extend(defaults, options);
		var backgroundColor = 'background-color';
		$(this).each(
				function() {
					var newTable = $(this);
					if (opts.isDrog) {
						drog(newTable);
					}
					$(newTable).find("tbody tr").css(backgroundColor,
							opts.oddtrBackgroundColor);
					var newtd = $(newTable).find('tbody td');
					newtd.mouseover(function() {
						var oldbackgroundColor = $(this).css(backgroundColor);
						if (opts.isEffect) {
							$(this).css({
								'cursor' : 'pointer',
								backgroundColor : opts.effectBackgroundColor
							});
							$(this).mouseout(function() {
								$(this).css({
									backgroundColor : oldbackgroundColor
								});
							});
						}
					});

					if (opts.isEditor) {
						newtd.click(function() {
							var td = $(this);
							if (td.children("input").length > 0) {
								return false;
							}
							// 向当前的td添加一个临时的input
							var inputObject = $("<input type='text'/>").height(
									"100%").width("100%").css({
								"font-size" : td.css("font-size"),
								backgroundColor : opts.editorBackgroundColor,
								"border-width" : 0
							});
							// 取消当前input文本框单击事件
							inputObject.click(function() {
								return false
							});
							// 获取td文本框放入一个变量
							var tdText = td.html();
							// 将td中的文本框赋值给input中
							inputObject.val(tdText);
							// 将input放入td文本框当中
							td.html(inputObject);
							// 选中编辑区域 兼容所有浏览器
							inputObject.trigger("focus").trigger("select");

							inputObject.blur(function() {
								// 恢复td的文本
								td.html(tdText);
								// 删除input
								$(this).remove();
							});

							// 键盘
							inputObject.keydown(function(event) {
								var keyCode = event.which;
								switch (keyCode) {
								case 13:
									var inputText = $(this).val();
									td.html(inputText);
									$(this).remove();
									if (!opts.isEditorNewColor)
										return false;
									td.css(backgroundColor,
											opts.editorNewColorBackgroundColor);
									break;
								case 27:
									td.html(tdText);
									$(this).remove();
									break;
							    }
						    });
				        });
					}

					//排序
					var title = $(newTable).find('tbody tr td');
					$(title).mouseover(function(){
					});
					$(title).click(function() {
						clickTable($(this));
					});
		     });
		function drog(table) {
			var tr = $(table).find("tbody tr");
			var th = $(table).find('tbody tr th');
			$(tr).mousemove(function(event) {
				$(this).css("cursor", "e-resize");
			});
			$(th).mousedown(function(event) {
				$(this).mousemove(function(e) {
					var width = $(this).width();
					var px = e.clientX - event.clientX;
					$(this).width(width + px);
				});
			});
			$(th).mouseup(function() {
				$(th).unbind('mousemove');
			});
		}
	};
})(jQuery);
