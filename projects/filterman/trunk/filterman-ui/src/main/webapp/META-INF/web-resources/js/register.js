$(function(){
    $('#username').blur(function(evt){
        var username = $(this).attr('value');
        $.get('check_username', {username: username}, function(data) {
            if (data == false) {
                $('#error-message').text('UserVO name ' + username + ' has been registered');
                $(this).addClass('error-box');
            }
        })
    })
})

