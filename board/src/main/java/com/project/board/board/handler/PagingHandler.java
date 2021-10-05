package com.project.board.board.handler;

public class PagingHandler {

    private int curPage;
    private int articleCntInAPage;
    private int offSet;


    // 최초 생성시 1페이지,이지당 10개 아티클 표시, offSet 0
    public PagingHandler() {
        this(1, 10);
        this.offSet = 0;
    }

    // 특정페이지에서 보여줄 아티클 offSet
    public PagingHandler(int curPage, int articleCntInAPage) {
        this.curPage = curPage;
        this.articleCntInAPage = articleCntInAPage;
        this.offSet = (curPage - 1) * articleCntInAPage;
    }

    public int getCurPage() {
        return curPage;
    }

    // 페이지 세팅시 offSet 계산
    public void setCurPage(Integer curPage) {

        if(curPage == null){
            return;
        }

        this.offSet = (curPage - 1) * this.articleCntInAPage;
        this.curPage = curPage;
    }

    public int getArticleCntInAPage() {
        return articleCntInAPage;
    }

    // 페이징은 10, 50, 100만 허용, default는 10
    public void setArticleCntInAPage(Integer articleCntInAPage) {

        if(articleCntInAPage == null){
            return;
        }

        if(articleCntInAPage == 10||
            articleCntInAPage== 50||
            articleCntInAPage== 100) {
            this.offSet = (this.curPage - 1) * articleCntInAPage;
            this.articleCntInAPage = articleCntInAPage;
        }
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }


}
