function search(){

  let sType = document.getElementById("searchType").value;
  let sKeyword = document.getElementById("searchKeyword").value;

    location.href = "/board/list?sType=" +sType+"&sKeyword="+sKeyword;

}