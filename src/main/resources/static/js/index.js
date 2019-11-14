$(function() {
	autoLeftNav();
    $(window).resize(function() {
        autoLeftNav();
//        console.log($(window).width())
    });
    loadPageHtml("show-process.html");
	$('.sidebar-nav-link > a').on('click',function(){
		var url = $(this).attr('data-url');
		loadPageHtml(url);
	});
})

function loadPageHtml(url){
	$.ajax({
		url : url,
		asycn : false,
		success : function(data) {
			$('.tpl-content-wrapper').empty();
			$('.tpl-content-wrapper').append(data);
		}
	});
}


function autoLeftNav() {
    $('.tpl-header-switch-button').on('click', function() {
        if ($('.left-sidebar').is('.active')) {
            if ($(window).width() > 1024) {
                $('.tpl-content-wrapper').removeClass('active');
            }
            $('.left-sidebar').removeClass('active');
        } else {

            $('.left-sidebar').addClass('active');
            if ($(window).width() > 1024) {
                $('.tpl-content-wrapper').addClass('active');
            }
        }
    })

    if ($(window).width() < 1024) {
        $('.left-sidebar').addClass('active');
    } else {
        $('.left-sidebar').removeClass('active');
    }
}


// 侧边菜单
$('.sidebar-nav-sub-title').on('click', function() {
    alert("aaaa");
    $(this).siblings('.sidebar-nav-sub').slideToggle(80)
        .end()
        .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
})

$(".sidebar-nav-sub").on('click', function(){
    $(".sidebar-nav-link > a").removeClass('active')
	$(this).children('a').addClass('active')
})
$(".sidebar-nav-link").on('click', function(){
    $(".sidebar-nav-link > a").removeClass('active')
	$(this).children('a').addClass('active')
})


