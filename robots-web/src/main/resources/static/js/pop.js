var SweetAlert = {
    info(title){
        swal(title);
    },
    info(title,msg){
        swal(title,msg);
    },

    error(title,msg){
        swal(title, msg, "error");
    },

    error(title){
        swal(title, "error");
    },

    warning(title,msg){
        swal(title, msg, "error");
    },

    warning(title){
        swal(title, "warning");
    },

    warning(title,msg){
        swal(title, msg, "warning");
    },



    timeClose(title,msg,time){
        swal({
            title: title,
            text: msg,
            timer: time
        });
    },

    confirm(title,msg){
        swal({
                title: title,
                text: msg,
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                cancelButtonText: '取消',
                confirmButtonText: '确认',
                closeOnConfirm: false,
                closeOnCancel: false
            },
            function(isConfirm){
                return isConfirm;
            });
    },

    ok(title,msg){
        swal({
            title: title,
            text: msg,
            imageUrl: '../../static/images/thumbs-up.jpg'
        });
    }
};
