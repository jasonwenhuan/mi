(function($) {
	$.fn.paginate = function(options) {
		var opts = $.extend({}, $.fn.paginate.defaults, options);
		return this.each(function() {
			$this = $(this);
			var o = $.meta ? $.extend({}, opts, $this.data()) : opts;
			var selectedpage = o.start;
			// check parameters
			if(o.count<1 || o.display<1 || o.start<1 || o.start>o.count){
				return;
			}
			$.fn.draw(o,$this,selectedpage);
		});
	};
	var outsidewidth_tmp = 0;
	var insidewidth 	 = 0;
	var bName = navigator.appName;
	var bVer = navigator.appVersion;
	if(bVer.indexOf('MSIE 7.0') > 0)
		var ver = "ie7";
	$.fn.paginate.defaults = {
		display : 3,
		count :  11,
		start : 6,
		pre_click : function(){return false;},
		next_click : function(){return false;},
		on_change : function(){return false;},
		first_click: function(){return false;},
		last_click: function(){return false;}
	};
	$.fn.draw = function(o,obj,selectedpage){
		// previous
		var _first = $(document.createElement('a')).addClass('jPag-pre').html('<<&nbsp;');
		var _previous = $(document.createElement('a')).addClass('jPag-pre').html('Previous');
		var _lsplitline = $(document.createElement('span')).addClass('jPag-left-split-line').html('&nbsp;|&nbsp;');
		var _spanwrapleft	= $(document.createElement('span'));
		_spanwrapleft.append(_first).append(_previous).append(_lsplitline);

		// next
		var _rsplitline = $(document.createElement('span')).addClass('jPag-right-split-line').html('&nbsp;|&nbsp;');
		var _next = $(document.createElement('a')).addClass('jPag-next').html('Next');
		var _last = $(document.createElement('a')).addClass('jPag-next').html('&nbsp;>>');
		var _spanwrapright = $(document.createElement('span'));
		_spanwrapright.append(_rsplitline).append(_next).append(_last);

		//show middle page number
		if(o.display%2>0) {
			var _firstpartnum = parseInt(o.display/2);
		}else{
			var _firstpartnum = parseInt(o.display/2) - 1;
		}
		var _lastpartnum = parseInt(o.display/2);

		var _firstpagenum = o.start*1 - _firstpartnum;
		var _lastpagenum = o.start*1 + _lastpartnum;

		_firstpagenum = (_firstpagenum > 1 ? _firstpagenum : 1);
		_lastpagenum = _firstpagenum*1 + o.display - 1;
		if(_lastpagenum > o.count) {
			_firstpagenum = _firstpagenum - (_lastpagenum - o.count);
			_firstpagenum = (_firstpagenum > 1 ? _firstpagenum : 1);
		}
		_lastpagenum = _firstpagenum*1 + o.display - 1;
		_lastpagenum = _lastpagenum > o.count ? o.count : _lastpagenum;

		var _ulwrapspan	= $(document.createElement('span'));
		_ulwrapspan.append("Page:");

		if(_firstpagenum != 1) {
			_ulwrapspan.append("...");
		}
		for(var i = _firstpagenum; i <= _lastpagenum; i++) {
			if(i == o.start){
				_ulwrapspan.append($(document.createElement('span')).addClass('jPag-span-num-selected').html(i));
			}else {
				_ulwrapspan.append($(document.createElement('a')).html(i));
			}
		}
		if(_lastpagenum != o.count) {
			_ulwrapspan.append("...");
		}

		// click a page
		_ulwrapspan.find('a').click(function(e){
			o.on_change($(this).html());
		});

		// add previoust event
		_previous.click(function(){
			o.pre_click(o.start);
		});
		// add next event
		_next.click(function(){
			o.next_click(o.start);
		});
		// add first event
		_first.click(function(){
			o.first_click(o.start);
		});
		// add last event
		_last.click(function(){
			o.last_click(o.start);
		});

		// apply the a style
		_ulwrapspan.find('a').addClass('jPg-page-num-a');
		_ulwrapspan.find('a').hover(
			function(){
				$(this).addClass('jPg-page-num-a-hover');
			},
			function(){
				$(this).removeClass();
				$(this).addClass('jPg-page-num-a');
			}
		);

		$this.append(_spanwrapleft).append(_ulwrapspan).append(_spanwrapright).addClass('jPag');
	}
})(jQuery);