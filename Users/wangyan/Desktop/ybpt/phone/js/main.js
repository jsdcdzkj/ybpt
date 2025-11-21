$(function(){	
  $('#member').on('click', function() {
        location.href='userCenter.html';
  });
  $('#rebate').on('click', function() {
        location.href='list.html';
  });
  $('#home').on('click', function() {
        location.href='main.html';
  });
  $('.btn-left').on('click', function() {
  	if (!$(this).hasClass('homeset')) {history.back();}else{location.href='message.html';}
        
  });
  $('.btn-right').on('click', function() {
        location.href='main.html';
  });

})