<c:if test="${f:containsKey(flash, 'notice')}">
<div id="notice" class="notice">${f:out(flash['notice'])}</div>
<script type="text/javascript">
	Event.observe(window,'load', function() {
		new Effect.Highlight('notice');
	});
</script>
</c:if>
