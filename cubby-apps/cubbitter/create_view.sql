
DROP VIEW public.comment_view;

CREATE VIEW public.comment_view AS
SELECT 
m.member_id, m.member_name, m.full_name, m.open,
c.comment_id, c.comment, c.post_time
FROM comment c
INNER JOIN member_comments mc
ON mc.comment_id = c.comment_id
INNER JOIN member m
ON mc.member_id = m.member_id
ORDER BY c.comment_id DESC