package br.com.rodolfocastanho.controle2M.controller.page;

import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PageWrapper_en<T> {

    private Page<T> page;
    private UriComponentsBuilder uriBuilder;

    public PageWrapper_en(Page<T> page, HttpServletRequest httpServletRequest) {
        this.page = page;
        //this.uriBuilder = uriBuilder;

        String httpUrl = httpServletRequest.getRequestURL().append(
                httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : ""
        ).toString().replaceAll("\\+", "%20");

        this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
    }

    public List<T> getListContent(){
        return page.getContent();
    }

    public boolean isEmptyPage(){
        return page.getContent().isEmpty();
    }

    public int getNumberActualPage(){
        return page.getNumber();
    }

    public boolean isFirstPage(){
        return page.isFirst();
    }

    public boolean isLastPage(){
        return page.isLast();
    }

    public int getTotal(){
        return page.getTotalPages();
    }

    /**
     *
     * Se tiver "page" na url, então troca pelo numero da página
     * Se não tiver, adiciona
     */
    public String urlGoToPage(int pagina) {
        return uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
    }

 }
