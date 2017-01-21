var app = {

    initialize: function(){
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },

    onDeviceReady: function(){
        FastClick.attach(document.body);
    },

    //显示加载器
    showLoader: function(){
        $.mobile.loading('show', {
            text: '加载中...', //加载器中显示的文字
            textVisible: true, //是否显示文字
            theme: 'b',        //加载器主题样式a-e
            textonly: false,   //是否只显示文字
            html: ""           //要显示的html内容，如图片等
        });
    },

    //隐藏加载器
    hideLoader: function(){
        //隐藏加载器
        $.mobile.loading('hide');
    }

};