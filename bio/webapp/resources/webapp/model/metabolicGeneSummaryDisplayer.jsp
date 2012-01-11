<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="gene-summary-displayer">
  <style>
  #gene-summary-displayer { margin-left:3px; }
  #gene-summary-displayer a { color:inherit; text-decoration:none; }
  #gene-summary-displayer li { margin:0; }
  #gene-summary-displayer ul li.main { display:inline-block; }
  #gene-summary-displayer ul li.main:not(:first-child) { margin-left:20px; }
  #gene-summary-displayer div.data { margin-right:10px; border-left:1px solid #fff; font-size:30px; font-weight:bold; }
  #gene-summary-displayer div.data:before { content:" "; float:left; display:inline-block; background:#d1d0d0; height:37px; width:1px; margin-right:10px; }
  #gene-summary-displayer div.data,
  #gene-summary-displayer div.label { float:left; }
  #gene-summary-displayer div.label span.title { font-size:15px; font-weight:bold; }
  #gene-summary-displayer div.label span.description { display:block; }
  </style>

  <ul>
    <c:forEach var="field" items="${summary.fields}">
      <c:if test="${field.value['data'] != null}">
        <li class="main">
          <a href="#${field.value['anchor']}" title="see more" class="link">
          <c:choose>
            <c:when test="${field.value['type'] == 'integer'}">
              <div class="data">${field.value['data']}</div>
              <div class="label">
                <span class="title">${field.key}</span>
                <span class="description">${field.value['description']}</span>
              </div>
            </c:when>
            <c:when test="${field.value['type'] == 'map'}">
              <div class="map">
                <div class="label">
                  <span class="title">${field.key}</span>
                  <span class="description">${field.value['description']}</span>
                 </div>
                <ul>
                <c:forEach var="innerField" items="${field.value['data']}">
                  <li>
                    <div class="data">${innerField.value}</div> <div class="label">${fn:replace(innerField.key, '_', ' ')}</div>
                  </li>
                </c:forEach>
                </ul>
              </div>
            </c:when>
            <c:when test="${field.value['type'] == 'image'}">
               <div class="data"><span id="placeholder-for-${field.key}"></span></div>
               <div class="label">
                 <span class="title">${field.key}</span>
                 <span class="description">${field.value['description']}</span>
               </div>
               <script type="text/javascript">
                 (function() {
                     jQuery(new Image())
                       .load(function() {
                         jQuery('span#placeholder-for-${field.key}').html(this);
                       })
                       .attr('src', "${field.value['data']}");
                 })();
               </script>
            </c:when>
            <c:otherwise>
              <div class="data">
                ${field.value['data']}
              </div>
              <div class="label">
                <span class="title">${field.key}</span>
                <span class="description">${field.value['description']}</span>
              </div>
            </c:otherwise>
          </c:choose>
          </a>
        </li>
      </c:if>
    </c:forEach>
  </ul>
  <div class="clear"></div>
  <script type="text/javascript">
  (function() {
    jQuery("#gene-summary-displayer li a").click(function(e){
      jQuery("a[name='" + jQuery(this).attr('href').replace('#', '') + "'].anchor").scrollTo('slow', 'swing', -20);
      e.preventDefault();
    });
  })();
  </script>
</div>