<<<<<<< HEAD

=======
>>>>>>> parent of 8eb053e (Merge branch 'main' of https://github.com/nerislj/SIG.git into main)
$(document).ready(function() {
	$('.menu li:has(ul)').click(function(e) {
		e.preventDefault();

		if($(this).hasClass('activado')) {
			$(this).removeClass('activado');
			$(this).children('ul').slideUp();
		} else {
			$('.menu li ul').slideUp();
			$('.menu li').removeClass('activado');
			$(this).addClass('activado');
			$(this).children('ul').slideDown();
		}

		$('.menu li ul li a').click(function() {
			window.location.href = $(this).attr('href');
		})
	});
<<<<<<< HEAD
});

$(document).ready(function() {
	$('.menu li:has(ul)').click(function(e) {
		e.preventDefault();

		if($(this).hasClass('activado')) {
			$(this).removeClass('activado');
			$(this).children('ul').slideUp();
		} else {
			$('.menu li ul').slideUp();
			$('.menu li').removeClass('activado');
			$(this).addClass('activado');
			$(this).children('ul').slideDown();
		}

		$('.menu li ul li a').click(function() {
			window.location.href = $(this).attr('href');
		})
	});
});



=======
});
>>>>>>> parent of 8eb053e (Merge branch 'main' of https://github.com/nerislj/SIG.git into main)
