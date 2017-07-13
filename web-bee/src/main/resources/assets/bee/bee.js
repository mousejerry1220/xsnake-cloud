$.extend({
	addTab : function (title, url) {
        window.top.addTab(title,url);
    },
    closeTab: function (title) {
    	window.top.closeTab(title);
    }
});

