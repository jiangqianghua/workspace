<?php

/* layout.html */
class __TwigTemplate_e63125d5c64868cb8e5c1ce1540ae08193972846c43d59c3779f8193fb1bb0a6 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
            'content' => array($this, 'block_content'),
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        // line 1
        echo "<html>
\t<body>
\t\t<header>header</header>
\t\t<content>
\t\t\t";
        // line 5
        $this->displayBlock('content', $context, $blocks);
        // line 7
        echo "\t\t</content>
\t\t<footer>footer</footer>
\t</body>
</html>";
    }

    // line 5
    public function block_content($context, array $blocks = array())
    {
        // line 6
        echo "\t\t\t";
    }

    public function getTemplateName()
    {
        return "layout.html";
    }

    public function getDebugInfo()
    {
        return array (  38 => 6,  35 => 5,  28 => 7,  26 => 5,  20 => 1,);
    }
}
/* <html>*/
/* 	<body>*/
/* 		<header>header</header>*/
/* 		<content>*/
/* 			{% block content %}*/
/* 			{% endblock %}*/
/* 		</content>*/
/* 		<footer>footer</footer>*/
/* 	</body>*/
/* </html>*/
