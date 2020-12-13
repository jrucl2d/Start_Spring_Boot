const replyManager = (()=>{
  const getAll = (obj, callback) => {
    $.getJSON('/replies/' + obj, callback);
  };

  const add = (obj, callback) => {
    $.ajax({
      type:'post',
      url: '/replies/' + obj.bno,
      data: JSON.stringify(obj),
      dataType:'json',
      beforeSend : function(xhr){
        xhr.setRequestHeader(obj.csrf.headerName, obj.csrf.token);
      },
      contentType: 'application/json',
      success:callback,
    })
  }

  const update = (obj, callback) => {
    $.ajax({
      type:'put',
      url: '/replies/' + obj.bno,
      data: JSON.stringify(obj),
      dataType:'json',
      beforeSend : function(xhr){
        xhr.setRequestHeader(obj.csrf.headerName, obj.csrf.token);
      },
      contentType: 'application/json',
      success:callback,
    })
  }

  const remove = (obj, callback) => {
    $.ajax({
      type:'delete',
      url: `/replies/${obj.bno}/${obj.rno}` ,
      dataType:'json',
      beforeSend : function(xhr){
        xhr.setRequestHeader(obj.csrf.headerName, obj.csrf.token);
      },
      contentType: 'application/json',
      success:callback,
    })
  }

  return {
    getAll,
    add,
    update,
    remove,
  }

})();
