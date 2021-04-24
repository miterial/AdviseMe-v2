$(document).ready(function(){
  $(".myslider").slick({

  infinite: true,
  slidesToShow: 4,
  arrows: true,
  prevArrow: '<span class=" slick-prev"></span>',
  nextArrow: '<span class=" slick-next"></span>',

  // the magic
  responsive: [{

      breakpoint: 1024,
      settings: {
        slidesToShow: 3,
        infinite: true
      }

    }, {

        breakpoint: 600,
        settings: {
          slidesToShow: 2,
          dots: true
      }

    }, {

    breakpoint: 300,
      settings: "unslick" // destroys slick

    }]
  })
});
