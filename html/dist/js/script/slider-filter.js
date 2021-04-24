// Years slider

var nonLinearSlider = document.getElementById('regular-slider');

noUiSlider.create(nonLinearSlider, {
  connect: true,
  behaviour: 'tap',
  start: [ 2010, 2018 ],
  range: {
    // Starting at 500, step the value by 500,
    // until 4000 is reached. From there, step by 1000.
    'min': 1955,
    'max': 2018
  },
  pips: {
    mode: 'values',
    values: [1955, 1970, 1985, 2000, 2018],
    density: 15,
    stepped: true
  },
  step:1,
  tooltips: true

});

// Управление клавишами
/*var handle = nonLinearSlider.querySelector('.noUi-handle');

handle.addEventListener('keydown', function( e ) {

  var value = Number( nonLinearSlider.noUiSlider.get() );

  if ( e.which === 37 ) {
    nonLinearSlider.noUiSlider.set( value - 10 );
  }

  if ( e.which === 39 ) {
    nonLinearSlider.noUiSlider.set( value + 10 );
  }
});*/


//Rating sliders

var ratingSlider1 = document.getElementById('rate-slider1');

noUiSlider.create(ratingSlider1, {
  connect: true,
  behaviour: 'tap',
  start: 0,
  range: {
    'min': 0,
    'max': 10
  },
  step: 0.5,
  tooltips: true
});

var ratingSlider2 = document.getElementById('rate-slider2');

noUiSlider.create(ratingSlider2, {
  connect: true,
  behaviour: 'tap',
  start: 0,
  range: {
    'min': 0,
    'max': 10
  },
  step: 0.5,
  tooltips: true
});

var ratingSlider3 = document.getElementById('rate-slider3');

noUiSlider.create(ratingSlider3, {
  connect: true,
  behaviour: 'tap',
  start: 0,
  range: {
    'min': 0,
    'max': 10
  },
  step: 0.5,
  tooltips: true
});