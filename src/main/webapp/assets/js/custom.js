$(function () {
    "use strict";

    /**
     * =====================
     *** JS TABLE
     * =====================
        01 - COMMING SOON PAGE JS
        02 - MAIN SLIDER JS
        03 - TESTIMONIAL SLIDER JS
        04 - ABOUT PAGE PARALLAX JS
        05 - TESTIMONIAL PARALLAX JS
        06 - BREADCRUMB JS
        07 - CLIENT JS
        08 - PORTFOLIO BTN JS
        09 - MAGNIFIC POPUP FOR PORTFOLIO  JS
        10 - BOTTOM TO TOP JS
        11 - AWARD COUNT JS
        12 - ISOTOPE JS
        13 - MOBILE MENU JS
        14 - RELOADER JS
     */



    /*================================================
    01 - COMMING SOON PAGE JS
    ==================================================*/
    var currentDate = new Date();
    $('div#clock').countdown(15 * 24 * 60 * 60 * 1000 + currentDate.valueOf(), function (event) {

        switch (event.type) {
            case "seconds":
            case "minutes":
            case "hours":
            case "days":
            case "weeks":
            case "daysLeft":
                $(this).find('span#' + event.type).html(event.value);
                break;
            case "finished":
                $(this).fadeTo('slow', .5);
                break;
        }
    });


    /*================================================
    02 - MAIN SLIDER JS
    ==================================================*/
    $('.slider-area-start').owlCarousel({
        animateOut: 'animate__fadeOutLeft',
        animateIn: 'animate__fadeInRight',
        loop: true,
        margin: 0,
        nav: false,
        dots: false,
        autoplay: false,
        autoplayHoverPause: true,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 1
            },
            1000: {
                items: 1
            }
        }
    });

    /*================================================
    03 - TESTIMONIAL SLIDER JS
    ==================================================*/
    $('.testimonial-slider-active').owlCarousel({
        animateOut: 'animate__fadeOutLeft',
        animateIn: 'animate__fadeInRight',
        loop: true,
        margin: 0,
        nav: true,
        dots: true,
        autoplay: true,
        autoplayHoverPause: true,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 1
            },
            1000: {
                items: 1
            }
        }
    });

    /*================================================
    04 - ABOUT PAGE PARALLAX JS
    ==================================================*/
    $('.parallax-window').parallax({
        imageSrc: 'assets/images/about/about-1.jpg',
        zIndex: 2,
        iosFix: true,
        androidFix: true
    });


    /*================================================
    05 - TESTIMONIAL PARALLAX JS
    ==================================================*/
    $('.testimonial-area-start').parallax({
//        imageSrc: 'assets/images/main-slider/slider-1.jpg',
        iosFix: true,
        androidFix: true
    });

    /*================================================
    06 - BREADCRUMB JS
    ==================================================*/
    $('.breadcrumb-area-start, .award-area-start').parallax({
//        imageSrc: 'assets/images/main-slider/slider-1.jpg',
        iosFix: true,
        androidFix: true
    });



    /*================================================
    07 - CLIENT JS
    ==================================================*/
    $('.client-slider-active').owlCarousel({
        animateOut: 'animate__fadeOutLeft',
        animateIn: 'animate__fadeInRight',
        loop: true,
        margin: 0,
        nav: false,
        dots: false,
        autoplay: true,
        autoplayHoverPause: true,
        responsive: {
            0: {
                items: 4
            },
            600: {
                items: 4
            },
            1000: {
                items: 6
            },
            1400: {
                items: 7
            }
        }
    });


    /*================================================
    08 - PORTFOLIO BTN JS
    ==================================================*/
    $('.portfolio-button button').click(function () {
        $('.portfolio-button button').removeClass('active');
        $(this).addClass('active');
    });

    var $grid1 = $('.portfolio-grid').isotope({
        itemSelector: '.single-portfolio',
        percentPosition: true,
        masonry: {
            // use outer width of grid-sizer for columnWidth
            columnWidth: '.single-portfolio'
        }
    });

    // filter items on button click
    $('.portfolio-button').on('click', 'button', function () {
        var filterValue = $(this).attr('data-filter');
        $grid1.isotope({
            filter: filterValue
        });
    });


    /*================================================
    08 - MAGNIFIC POPUP FOR PORTFOLIO  JS
    ==================================================*/
    $('.mag-image').magnificPopup({
        type: 'image',
        mainClass: 'portfolio-grid',
        gallery: {
            enabled: true
        }
    });


    /*================================================
    10 - BOTTOM TO TOP JS
    ==================================================*/
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.btm-to-top').show();
        } else {
            $('.btm-to-top').hide();
        }
    });

    $('.btm-to-top').click(function () {
        $('html, body').animate({
            scrollTop: 0
        }, 1000);
    });



    /**
     * Navigation fixed in top
     * JS 
     */
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.navigation-area-start').addClass('fixed');
        }
        if ($(this).scrollTop() <= 299) {
            $('.navigation-area-start').removeClass('fixed');
        }

    });



    /*================================================
    11 - AWARD COUNT JS
    ==================================================*/
    $('.award-counter').counterUp({
        delay: 10,
        time: 3000
    });

    /*================================================
    12 - ISOTOPE JS
    ==================================================*/
    var $grid = $('.grid').isotope({
        itemSelector: '.element-item',
        percentPosition: true,
        masonry: {
            // use outer width of grid-sizer for columnWidth
            columnWidth: '.grid-sizer'
        }
    });
    // filter items on button click
    $('.filter-button-group').on('click', 'button', function () {
        var filterValue = $(this).attr('data-filter');
        $grid.isotope({
            filter: filterValue
        });
    });




    /*================================================
    13 - MOBILE MENU JS
    ==================================================*/
    $(".main-menu").slicknav({
        prependTo: ".main-menu-area"
    });

    /*================================================
    14 - RELOADER JS
    ==================================================*/
    setTimeout(function () {
        $('#preloader').fadeOut();
    }, 2500);

}(jQuery));
