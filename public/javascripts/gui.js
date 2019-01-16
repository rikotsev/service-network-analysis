;(function(undefined) {
    'use strict';

    if (typeof sigma === 'undefined')
        throw 'sigma is not declared';

    // Initialize packages:
    sigma.utils.pkg('sigma.canvas.labels');

    /**
     * This label renderer will just display the label on the right of the node.
     *
     * @param  {object}                   node     The node object.
     * @param  {CanvasRenderingContext2D} context  The canvas context.
     * @param  {configurable}             settings The settings function.
     */
    sigma.canvas.labels.def = function(node, context, settings) {
        var fontSize,
            prefix = settings('prefix') || '',
            size = node[prefix + 'size'];

        //if (size < settings('labelThreshold'))
        //   return;

        if (!node.label || typeof node.label !== 'string')
            return;

        fontSize = (settings('labelSize') === 'fixed') ?
            settings('defaultLabelSize') :
            settings('labelSizeRatio') * size;

        context.font = (settings('fontStyle') ? settings('fontStyle') + ' ' : '') +
            fontSize + 'px ' + settings('font');
        context.fillStyle = (settings('labelColor') === 'node') ?
            (node.color || settings('defaultNodeColor')) :
            settings('defaultLabelColor');

        context.fillText(
            node.label,
            Math.round(node[prefix + 'x'] + size + 3),
            Math.round(node[prefix + 'y'] + fontSize / 3)
        );
    };
}).call(this);


function fnFillGraphFrameViz(objSettings) {
    $(document).ready(function () {
       var strXMLUrl = objSettings.url;
       var strSigmaContainer = objSettings.containerId;
       var domSigmaContainer = $('#' + strSigmaContainer);

       var objData = graphFormJs;

       $.post({
           url: strXMLUrl,
           data: objData
       }).done(function (xmlGraphSrc) {
           domSigmaContainer.html('');
           sigma.parsers.gexf(
               xmlGraphSrc,
               {
                   container: strSigmaContainer,
                   drawEdgeLabels: true,
                   defaultEdgeColor: '#ccc',
                   edgeColor: 'default',
                   labelThreshold: 3,
                   drawLabels: true
               },
               function (s) {

               }
           )
       });

    });
}

function fnFillGraphFrameData(objSettings) {
    $(document).ready(function () {
        var strDataUrl = objSettings.url;
        var strDataContainer = objSettings.containerId;
        var domDataContainer = $('#' + strDataContainer);

        var objData = graphFormJs;

        $.post({
            url: strDataUrl,
            data: objData
        }).done(function (htmlDetails) {
            domDataContainer.html(htmlDetails);
        });

    });
}