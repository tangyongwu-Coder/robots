var GritterAlert = {
    warning(title,text){
        $.gritter.add({
            title: title,
            text: text,
            image: '../../images/alert.png',
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
    info(title,text){
        $.gritter.add({
            title: title,
            text: text,
            image: '../../images/ok.png',
            sticky: false,
            before_open: function(){
                if($('.gritter-item-wrapper').length == 3)
                {
                    return false;
                }
            }
        });
    },
};


var SweetAlert = {
    info(title){
        swal(title);
    },
    info(title,text){
        swal(title,text);
    },

    errorFun(title,text){
        swal(title, text, "error");
    },

    errorFun(title){
        swal(title, "error");
    },

    warning(title,text){
        swal(title, text, "error");
    },

    warning(title){
        swal(title, "warning");
    },

    warning(title,text){
        swal(title, text, "warning");
    },



    timeClose(title,text,time){
        swal({
            title: title,
            text: text,
            timer: time
        });
    },

    confirm(option){
        swal({
                title: option.title,
                text: option.text,
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                cancelButtonText: '取消',
                confirmButtonText: '确认',
                closeOnConfirm: false,
            },
            function(){
                option.confirmFun();
                $('.sweet-alert').remove();
                $('.sweet-overlay').remove();
            });
    },

    ok(title,text){
        swal({
            title: title,
            text: text,
            imageUrl: '../../images/thumbs-up.jpg'
        });
    }
};
