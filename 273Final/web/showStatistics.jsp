<%@ page import="java.util.ArrayList" %>
<%@ page import="servletManager.model.CountByHour" %>
<%@ page import="servletManager.functions.OperateMongoDBCollections" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Spurthy
  Date: 5/13/2015
  Time: 2:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Spurthy
  Date: 5/13/2015
  Time: 2:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>CMPE 273 Project</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/style-responsive.css" rel="stylesheet">

    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>




    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<section id="container" >
    <!-- **********************************************************************************************************************************************************
    TOP BAR CONTENT & NOTIFICATIONS
    *********************************************************************************************************************************************************** -->
    <!--header start-->
    <header class="header black-bg">
        <div class="sidebar-toggle-box">
            <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
        </div>
        <!--logo start-->
        <a  class="logo"><b>Twitter Hash Tag Analysis - Using Aws Lambda</b></a>
        <!--logo end-->
        <div class="nav notify-row" id="top_menu">

        </div>
        <div class="top-menu">
            <ul class="nav pull-right top-menu">
                <li><a class="logout" href="tweets.html">Home</a></li>
                <li><a class="logout" href="login.html">Login</a></li>
            </ul>
        </div>
    </header>
    <!--header end-->

    <!-- **********************************************************************************************************************************************************
    MAIN SIDEBAR MENU
    *********************************************************************************************************************************************************** -->
    <!--sidebar start-->
    <aside>
        <div id="sidebar"  class="nav-collapse ">
            <!-- sidebar menu start-->
            <ul class="sidebar-menu" id="nav-accordion">

                <p class="centered"><a href="profile.html"><img src="assets/img/twitter.png" class="img-circle" width="60"></a></p>

                <li class="sub-menu">
                    <a href="javascript:;" >

                        <span>   Twitter - AWS - Lambda</span>
                    </a>

                </li>

            </ul>
            <!-- sidebar menu end-->
        </div>
    </aside>
    <!--sidebar end-->

    <!-- **********************************************************************************************************************************************************
    MAIN CONTENT
    *********************************************************************************************************************************************************** -->
    <!--main content start-->
    <section id="main-content">
        <section class="wrapper site-min-height">

            <div class="row mt">
                <div class="col-lg-12">

                    <!-- BASIC FORM ELELEMNTS -->
                    <div class="row mt">
                        <div class="col-lg-12">
                            <div class="form-panel">
                                <h4 class="mb"><i class="fa fa-angle-right"></i> Tweets Info:</h4>
                                <form class="form-horizontal style-form" method="get">
                                    <div class="form-group">

                                        <div class="col-sm-10">
                                            <div id="container1" ></div>
                                        </div>
                                    </div>

                                </form>
                            </div>
                        </div><!-- col-lg-12-->
                    </div><!-- /row -->


                </div>
            </div>

        </section><! --/wrapper -->
    </section><!-- /MAIN CONTENT -->

    <!--main content end-->
    <!--footer start-->
    <footer class="site-footer">
        <div class="text-center">
            CMPE 273
            <a href="blank.html#" class="go-top">
                <i class="fa fa-angle-up"></i>
            </a>
        </div>
    </footer>
    <!--footer end-->
</section>

<!-- js placed at the end of the document so the pages load faster -->

<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script>


<script src="assets/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
<script class="include" type="text/javascript" src="assets/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="assets/js/jquery.scrollTo.min.js"></script>
<script src="assets/js/jquery.nicescroll.js" type="text/javascript"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">


        <%
        List<CountByHour> countByHourList1 = OperateMongoDBCollections.addTagCountByHourFromDBTweets1();
        List<Integer> countOfTweet1 = new ArrayList<Integer>();
        for(CountByHour countByHour : countByHourList1){
         countOfTweet1.add(countByHour.getCount());
         System.out.println(countByHour.getCount());
        }

        %>

        var countsTweet1 =[] ;



        <% for (int i=0; i<countOfTweet1.size(); i++) { %>
        countsTweet1[<%= i %>] = "<%= countOfTweet1.get(i) %>";
        <% } %>

        <%
       List<CountByHour> countByHourList2 = OperateMongoDBCollections.addTagCountByHourFromDBTweets2();
       List<Integer> countOfTweet2 = new ArrayList<Integer>();
       for(CountByHour countByHour : countByHourList2){
        countOfTweet1.add(countByHour.getCount());
         System.out.println(countByHour.getCount());
       }

       %>

        var countsTweet2 = [<% for (int i = 0; i < countOfTweet2.size(); i++) { %>"<%= countOfTweet2.get(i) %>"<%= i + 1 < countOfTweet2.size() ? ",":"" %><% } %>];
        <% for (int i=0; i<countOfTweet2.size(); i++) { %>
        xTime[<%= i %>] = "<%= countOfTweet2.get(i) %>";
        <% } %>

    google.load('visualization', '1.1', {packages: ['line']});
    google.setOnLoadCallback(drawChart);

    function drawChart() {
        alert(countsTweet1+" "+countsTweet1[0]);
        var data = new google.visualization.DataTable();
        data.addColumn('number', 'Day');
        data.addColumn('number', 'Guardians of the Galaxy');


        for(var i=0;i<countsTweet1.length;i++){
            console.log(i+" "+countsTweet1[i]+" "+countsTweet1[i]);
        data.addRow([number(countsTweet1[i]),number(countsTweet1[i])]);
        }
       /* data.addRows([
            [1,  37.8, 80.8, 41.8],
            [2,  30.9, 69.5, 32.4],
            [3,  25.4,   57, 25.7],
            [4,  11.7, 18.8, 10.5],
            [5,  11.9, 17.6, 10.4],
            [6,   8.8, 13.6,  7.7],
            [7,   7.6, 12.3,  9.6],
            [8,  12.3, 29.2, 10.6],
            [9,  16.9, 42.9, 14.8],
            [10, 12.8, 30.9, 11.6],
            [11,  5.3,  7.9,  4.7],
            [12,  6.6,  8.4,  5.2],
            [13,  4.8,  6.3,  3.6],
            [14,  4.2,  6.2,  3.4]
        ]);
*/
        var options = {
            chart: {
                title: 'Box Office Earnings in First Two Weeks of Opening',
                subtitle: 'in millions of dollars (USD)'
            },
            width: 900,
            height: 500,
            axes: {
                x: {
                    0: {side: 'top'}
                }
            }
        };

        var chart = new google.charts.Line(document.getElementById('container1'));

        chart.draw(data, options);
    }
</script>
</body>
</html>
