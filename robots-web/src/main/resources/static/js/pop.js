var SweetAlert = {
    warning(title,text){
        $.gritter.add({
            title: title,
            text: text,
            image: 'images/alert.png',
            sticky: false,
            class_name: 'gritter-light',
            before_open: function(){
                if($('.gritter-item-wrapper').length == 3)
                {
                    return false;
                }
            }
        });
    },

};
